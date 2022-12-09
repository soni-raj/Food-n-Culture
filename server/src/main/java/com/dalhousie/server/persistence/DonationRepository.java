package com.dalhousie.server.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dalhousie.server.model.Donation;

@Component
public class DonationRepository implements IDonationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Donation> findAll() {
        return jdbcTemplate.query("CALL getAllDonations()", BeanPropertyRowMapper.newInstance(Donation.class));
    }

    @Override
    public int save(Donation donation) {
        return jdbcTemplate.update(
                "CALL createDonation(?, ?, ?, ?, ?, ?, ?, ?)",
                donation.getId(), donation.getEventId(), donation.getName(), donation.getAmount(), donation.getEmail(),
                donation.getNote(), donation.getUpdatedAt(), donation.getCreatedAt());
    }

    @Override
    public int update(Donation donation) {
        return jdbcTemplate.update(
                "CALL updateDonation(?, ?, ?, ?, ?, ?)",
                donation.getEventId(), donation.getName(), donation.getAmount(), donation.getEmail(),
                donation.getNote(), donation.getId());
    }

    @Override
    public int delete(Donation donation) {
        return jdbcTemplate.update("CALL deleteDonation(?)", donation.getId());
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update("CALL deleteDonation(?)", id);
    }

    @Override
    public boolean exists(Integer id) {
        return false;
    }

    @Override
    public Optional<Donation> getById(Integer id) {
        try {
            Donation donation = jdbcTemplate.queryForObject("CALL getDonationById(?)", BeanPropertyRowMapper.newInstance(Donation.class), id);
            return Optional.of(donation);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Donation> getDonationsByEventId(Integer eventId) {
        return jdbcTemplate.query("CALL getDonationsByEventId(?)", BeanPropertyRowMapper.newInstance(Donation.class), eventId);
    }

}