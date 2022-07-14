package com.mykhailo_pavliuk.smart_cookie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;

@Entity
@Table(name = "language")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Language {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "language")
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
    Language language = (Language) o;
    return id != null && Objects.equals(id, language.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
