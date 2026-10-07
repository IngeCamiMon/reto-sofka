package co.reto.sofka.opencart.models;

public record GuestDetails(
        String firstName,
        String lastName,
        String email,
        String telephone,
        String address,
        String city,
        String postcode
) {
}
