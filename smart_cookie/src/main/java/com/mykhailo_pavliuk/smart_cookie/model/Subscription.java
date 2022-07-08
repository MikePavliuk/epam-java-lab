package com.mykhailo_pavliuk.smart_cookie.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "subscription")
@NamedQuery(
    name = "Subscription.getAllSubscriptionsByUserId",
    query = "SELECT s FROM Subscription s WHERE s.user.id = :userId")
@NamedNativeQuery(
    name = "Subscription.subscribeUserToPublication",
    query =
        "INSERT INTO subscription (user_id, publication_id, period_in_months, start_date) VALUES (?1, ?2, ?3, ?4)")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

  @EmbeddedId private UserPublicationPK id;

  @ManyToOne(cascade = CascadeType.ALL)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("publicationId")
  @JoinColumn(name = "publication_id")
  private Publication publication;

  private LocalDate startDate;
  private Integer periodInMonths;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Subscription that = (Subscription) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
