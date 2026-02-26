package com.anvar.em.employee_management.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_information")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactInformation extends BaseEntity{

    @Column(name = "phone_number", unique = true, length = 11)
    private String phoneNumber;

    @Column(name = "address_line_one", nullable = true)
    private String addressLine1;

    @Column(name = "address_line_two", nullable = true)
    private String addressLine2;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "zip_code", length = 6, nullable = true)
    private String zipCode;
}
