package com.mykhailo_pavliuk.smart_cookie.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;

@Entity
@Table(name = "publication")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;

  private BigDecimal pricePerMonth;

  @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private Set<Subscription> subscriptions;

  @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private Set<PublicationInfo> publicationInfos;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Publication that = (Publication) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
