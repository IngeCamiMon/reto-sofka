Feature: PetStore user lifecycle
  Exercise create, read, update and delete for a unique user through the public API.

  Background:
    * url baseUrl
    * configure retry = { count: 5, interval: 1000 }
    * def UUID = Java.type('java.util.UUID')
    * def BigInteger = Java.type('java.math.BigInteger')
    * def username = 'karate_' + UUID.randomUUID().toString().replace('-', '')
    * def createdEmail = username + '@example.test'
    * def createdUser =
      """
      {
        username: '#(username)',
        firstName: 'Karate',
        lastName: 'Original',
        email: '#(createdEmail)',
        password: 'test-only-password',
        phone: '5550102030',
        userStatus: 1
      }
      """

  Scenario: Create, read, update, read and delete a unique user
    Given path 'user'
    And request createdUser
    And print 'POST /user input:', createdUser
    When method post
    Then status 200
    And print 'POST /user output:', response
    And match response == { code: 200, type: '#string', message: '#string' }
    And match response.type == 'unknown'
    * def createResponse = response

    Given path 'user', username
    And print 'GET /user/{username} input username:', username
    And retry until responseStatus == 200
    When method get
    Then status 200
    And print 'GET /user/{username} output:', response
    And match response contains createdUser
    And match response.username == username
    And match response.firstName == 'Karate'
    And match response.lastName == 'Original'
    And match response.email == createdUser.email
    And match response.phone == createdUser.phone
    And match response.userStatus == 1
    * def getCreatedResponse = response
    * match getCreatedResponse.id == '#number'
    * copy updatedUser = getCreatedResponse
    * set updatedUser.id = new BigInteger(createResponse.message)
    * set updatedUser.firstName = 'Karate Updated'
    * set updatedUser.email = username + '.updated@example.test'

    Given path 'user', username
    And print 'PUT /user/{username} input:', updatedUser
    And request updatedUser
    When method put
    Then status 200
    And print 'PUT /user/{username} output:', response
    And match response == { code: 200, type: '#string', message: '#string' }
    And match response.type == 'unknown'
    And match response.message == createResponse.message
    * def updateResponse = response

    Given path 'user', username
    And print 'GET updated /user/{username} input username:', username
    And retry until responseStatus == 200 && response.firstName == 'Karate Updated' && response.email == updatedUser.email
    When method get
    Then status 200
    And print 'GET updated /user/{username} output:', response
    And match response contains updatedUser
    And match response.username == username
    And match response.firstName == 'Karate Updated'
    And match response.email == updatedUser.email
    And match response.lastName == 'Original'
    And match response.phone == updatedUser.phone
    And match response.userStatus == 1
    * def getUpdatedResponse = response

    Given path 'user', username
    And print 'DELETE /user/{username} input username:', username
    When method delete
    Then status 200
    And print 'DELETE /user/{username} output:', response
    And match response == { code: 200, type: '#string', message: '#string' }
    And match response.type == 'unknown'
    And match response.message == username
    * def deleteResponse = response

    Given path 'user', username
    And print 'GET after DELETE /user/{username} input username:', username
    And retry until responseStatus == 404
    When method get
    Then status 404
    And print 'GET after DELETE /user/{username} output:', response
    And match response.code == 1
    And match response.type == 'error'
    And match response.message == 'User not found'
    * def getDeletedResponse = response
