package com.mykhailo_pavliuk.smart_cookie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import com.mykhailo_pavliuk.smart_cookie.model.Language;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class PublicationInfoDto {

  @Id
  @Null(message = "{validation.id.null}", groups = OnCreate.class)
  @NotNull(message = "{validation.id.not_null}", groups = OnUpdate.class)
  private Long id;

  @NotNull(message = "{validation.language.not_null}")
  private Language language;

  @Pattern(regexp = ".{2,100}", message = "{validation.publication.title}")
  private String title;

  @Pattern(regexp = ".{10,255}", message = "{validation.publication.description}")
  private String description;

  @JsonIgnore @ToString.Exclude private PublicationDto publication;
}
