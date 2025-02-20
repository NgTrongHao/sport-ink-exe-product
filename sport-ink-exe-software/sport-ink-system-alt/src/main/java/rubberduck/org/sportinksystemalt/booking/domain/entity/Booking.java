package rubberduck.org.sportinksystemalt.booking.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.administration.domain.entity.Sport;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.shared.common.util.RandomStringUtil;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "booking", indexes = {}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "booking_duration")
    private Integer bookingDuration;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_sport_id")
    @ToString.Exclude
    private Sport bookingSport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_playfield_id")
    @ToString.Exclude
    private Playfield bookingPlayfield;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_playfield_sport_id")
    @ToString.Exclude
    private PlayfieldSport playfieldSport;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "booking_price")
    private Double bookingPrice;

    @Column(name = "booking_note", columnDefinition = "TEXT")
    private String bookingNote;

    @Column(name = "booking_code")
    private String bookingCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<BookingHistory> bookingHistoryList;

    @PrePersist
    public void prePersist() {
        this.startDateTime = LocalDateTime.of(bookingDate, bookingTime);
        this.endDateTime = startDateTime.plusMinutes(bookingDuration);
        this.bookingCode = RandomStringUtil.randomAlphaNumeric(6);
    }
}
