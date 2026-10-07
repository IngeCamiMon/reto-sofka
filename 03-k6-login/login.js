import http from 'k6/http';
import { check } from 'k6';
import { Counter, Rate } from 'k6/metrics';
import { SharedArray } from 'k6/data';
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';

const users = new SharedArray('login test users', () => {
  const csv = papaparse.parse(open('./data/usuarios.csv'), {
    header: true,
    skipEmptyLines: 'greedy',
  });

  if (csv.errors.length > 0) {
    throw new Error(`Could not parse login CSV: ${JSON.stringify(csv.errors)}`);
  }
  if (csv.data.length === 0 || !csv.meta.fields.includes('user') || !csv.meta.fields.includes('passwd')) {
    throw new Error('Login CSV must contain user and passwd columns and at least one data row');
  }

  return csv.data.map((row, index) => {
    if (!row.user || !row.passwd) {
      throw new Error(`Login CSV row ${index + 2} must contain user and passwd`);
    }
    return row;
  });
});

const acceptedResponses = new Counter('login_accepted_responses');
const tokenResponses = new Counter('login_token_responses');
const loginFailures = new Rate('login_failure_rate');

export const options = {
  scenarios: {
    sustained_login: {
      executor: 'constant-arrival-rate',
      rate: 20,
      timeUnit: '1s',
      duration: '30s',
      preAllocatedVUs: 40,
      maxVUs: 80,
      gracefulStop: '30s',
      exec: 'login',
    },
  },
  thresholds: {
    http_req_duration: ['p(95)<1500'],
    http_req_failed: ['rate<0.03'],
    iterations: ['count>=600'],
    dropped_iterations: ['count==0'],
    checks: ['rate==1'],
    login_failure_rate: ['rate<0.03'],
  },
};

export function login() {
  const user = users[(__VU + __ITER - 1) % users.length];
  const response = http.post(
    'https://fakestoreapi.com/auth/login',
    JSON.stringify({ username: user.user, password: user.passwd }),
    {
      headers: { 'Content-Type': 'application/json' },
      tags: { name: 'POST /auth/login' },
    },
  );

  let tokenPresent = false;
  try {
    const body = response.json();
    tokenPresent = typeof body.token === 'string' && body.token.length > 0;
  } catch (_) {
    tokenPresent = false;
  }

  const acceptedStatus = response.status === 200 || response.status === 201;
  const withinResponseLimit = response.timings.duration <= 1500;
  const checksPassed = check(response, {
    'login returns HTTP 200 or 201': () => acceptedStatus,
    'login response contains a non-empty token': () => tokenPresent,
    'login response completes within 1500 ms': () => withinResponseLimit,
  });

  if (acceptedStatus) {
    acceptedResponses.add(1);
  }
  if (tokenPresent) {
    tokenResponses.add(1);
  }
  loginFailures.add(!checksPassed);
}

function value(data, metricName, key) {
  const metric = data.metrics[metricName];
  return metric && metric.values && metric.values[key] !== undefined
    ? metric.values[key]
    : null;
}

function format(valueToFormat, digits = 2) {
  return valueToFormat === null ? 'not available' : Number(valueToFormat).toFixed(digits);
}

function thresholdResults(data) {
  const names = [
    'http_req_duration',
    'http_req_failed',
    'iterations',
    'dropped_iterations',
    'checks',
    'login_failure_rate',
  ];

  return Object.fromEntries(names.map((name) => [
    name,
    data.metrics[name] && data.metrics[name].thresholds
      ? data.metrics[name].thresholds
      : {},
  ]));
}

export function handleSummary(data) {
  const httpRequests = value(data, 'http_reqs', 'count');
  const httpDurationP95Ms = value(data, 'http_req_duration', 'p(95)');
  const httpDurationMaxMs = value(data, 'http_req_duration', 'max');
  const httpFailedRate = value(data, 'http_req_failed', 'rate');
  const checksFailed = value(data, 'checks', 'fails');
  const droppedIterations = value(data, 'dropped_iterations', 'count');
  const completedIterations = value(data, 'iterations', 'count');

  const summary = {
    generatedAt: new Date().toISOString(),
    endpoint: 'POST https://fakestoreapi.com/auth/login',
    k6Scenario: {
      executor: 'constant-arrival-rate',
      targetIterationsPerSecond: 20,
      durationSeconds: 30,
      preAllocatedVUs: 40,
      maxVUs: 80,
    },
    results: {
      httpRequests,
      completedIterations,
      k6HttpRequestRateIncludingResponseDrain: value(data, 'http_reqs', 'rate'),
      configuredArrivalRateIterationsPerSecond: 20,
      httpFailedRate,
      httpDurationP95Ms,
      httpDurationMaxMs,
      checksPassed: value(data, 'checks', 'passes'),
      checksFailed,
      acceptedLoginResponses: value(data, 'login_accepted_responses', 'count'),
      responsesWithToken: value(data, 'login_token_responses', 'count'),
      failedLoginIterationRate: value(data, 'login_failure_rate', 'rate'),
      droppedIterations,
      testDurationMs: data.state.testRunDurationMs,
    },
    thresholdChecks: {
      executorScheduled20IterationsPerSecond:
        completedIterations >= 600 && droppedIterations === 0,
      p95Below1500Ms: httpDurationP95Ms !== null && httpDurationP95Ms < 1500,
      everyResponseAtMost1500Ms: httpDurationMaxMs !== null && httpDurationMaxMs <= 1500,
      httpFailureRateBelow3Percent: httpFailedRate !== null && httpFailedRate < 0.03,
      allChecksPassed: checksFailed === 0,
      noDroppedIterations: droppedIterations === 0,
    },
    thresholds: thresholdResults(data),
  };

  const html = `<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>K6 login load test summary</title>
  <style>
    body{font:16px system-ui,sans-serif;max-width:900px;margin:2rem auto;padding:0 1rem;color:#20242a}
    table{border-collapse:collapse;width:100%}th,td{border:1px solid #d8dde3;padding:.65rem;text-align:left}
    th{background:#f2f5f8}.pass{color:#137333}.raw{white-space:pre-wrap;overflow-wrap:anywhere;background:#f6f8fa;padding:1rem}
  </style>
</head>
<body>
  <h1>K6 login load test</h1>
  <p>${summary.endpoint}</p>
  <p>Target arrival rate: 20 iterations/s for 30 seconds. Each iteration sends one login request.</p>
  <table>
    <thead><tr><th>Metric</th><th>Observed</th><th>Target</th></tr></thead>
    <tbody>
      <tr><td>HTTP requests</td><td>${format(summary.results.httpRequests, 0)}</td><td>600 scheduled</td></tr>
      <tr><td>Configured arrival rate</td><td>${format(summary.results.configuredArrivalRateIterationsPerSecond, 2)} iterations/s</td><td>20 iterations/s for the full 30 s load window</td></tr>
      <tr><td>Completed iterations / HTTP requests</td><td>${format(summary.results.completedIterations, 0)} / ${format(summary.results.httpRequests, 0)}</td><td>600 scheduled; zero dropped</td></tr>
      <tr><td>K6 HTTP response completion rate</td><td>${format(summary.results.k6HttpRequestRateIncludingResponseDrain, 2)} req/s</td><td>Informational; includes response drain after arrival window</td></tr>
      <tr><td>HTTP request failure rate</td><td>${format(summary.results.httpFailedRate * 100)}%</td><td>&lt; 3%</td></tr>
      <tr><td>Response duration p95</td><td>${format(summary.results.httpDurationP95Ms)} ms</td><td>&lt; 1500 ms</td></tr>
      <tr><td>Maximum response duration</td><td>${format(summary.results.httpDurationMaxMs)} ms</td><td>&le; 1500 ms for every request</td></tr>
      <tr><td>Checks</td><td>${format(summary.results.checksPassed, 0)} passed / ${format(summary.results.checksFailed, 0)} failed</td><td>All pass</td></tr>
      <tr><td>Dropped iterations</td><td>${format(summary.results.droppedIterations, 0)}</td><td>0</td></tr>
    </tbody>
  </table>
  <h2>Acceptance checks</h2>
  <ul>${Object.entries(summary.thresholdChecks).map(([name, passed]) => `<li class="${passed ? 'pass' : ''}">${name}: ${passed ? 'PASS' : 'FAIL'}</li>`).join('')}</ul>
  <h2>Raw K6 summary</h2>
  <pre class="raw">${JSON.stringify(summary, null, 2).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')}</pre>
</body>
</html>`;

  return {
    'reportes/resumen.json': JSON.stringify(summary, null, 2),
    'reportes/resumen.html': html,
  };
}
