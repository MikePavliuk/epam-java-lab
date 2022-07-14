package com.mykhailo_pavliuk.smart_cookie.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.Hibernate;

@Embeddable
public class UserPublicationPK implements Serializable {
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "publication_id")
  private Long publicationId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    UserPublicationPK that = (UserPublicationPK) o;
    return userId != null
        && Objects.equals(userId, that.userId)
        && publicationId != null
        && Objects.equals(publicationId, that.publicationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, publicationId);
  }
}
