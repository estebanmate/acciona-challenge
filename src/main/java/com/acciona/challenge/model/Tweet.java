package com.acciona.challenge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

  public static final String USERNAME = "username";
  public static final String TEXT = "text";
  public static final String LOCALE = "locale";
  public static final String VALIDATED = "validated";

  @Id
  private Long Id;

  @Column(USERNAME)
  private String username;

  @Column(TEXT)
  private String text;

  @Column(LOCALE)
  private String locale;

  @Column(VALIDATED)
  private boolean validated;

}
