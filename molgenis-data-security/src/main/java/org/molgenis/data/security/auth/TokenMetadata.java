package org.molgenis.data.security.auth;

import static java.util.Objects.requireNonNull;
import static org.molgenis.data.meta.AttributeType.DATE_TIME;
import static org.molgenis.data.meta.AttributeType.TEXT;
import static org.molgenis.data.meta.AttributeType.XREF;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_LABEL;
import static org.molgenis.data.meta.model.Package.PACKAGE_SEPARATOR;
import static org.molgenis.data.security.auth.SecurityPackage.PACKAGE_SECURITY;

import org.molgenis.data.meta.SystemEntityType;
import org.springframework.stereotype.Component;

@Component
public class TokenMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "Token";
  public static final String TOKEN = PACKAGE_SECURITY + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String TOKEN_ATTR = "token";
  public static final String ID = "id";
  public static final String USER = "User";
  public static final String EXPIRATIONDATE = "expirationDate";
  public static final String CREATIONDATE = "creationDate";
  public static final String DESCRIPTION = "description";

  private final SecurityPackage securityPackage;
  private final UserMetadata userMetadata;

  TokenMetadata(SecurityPackage securityPackage, UserMetadata userMetadata) {
    super(SIMPLE_NAME, PACKAGE_SECURITY);
    this.securityPackage = requireNonNull(securityPackage);
    this.userMetadata = requireNonNull(userMetadata);
  }

  @Override
  public void init() {
    setLabel(SIMPLE_NAME);
    setPackage(securityPackage);

    addAttribute(ID, ROLE_ID).setAuto(true).setVisible(false);
    addAttribute(USER)
        .setDataType(XREF)
        .setRefEntity(userMetadata)
        .setAggregatable(true)
        .setNillable(false);
    addAttribute(TOKEN_ATTR, ROLE_LABEL).setLabel(SIMPLE_NAME).setUnique(true).setNillable(false);
    addAttribute(EXPIRATIONDATE)
        .setDataType(DATE_TIME)
        .setLabel("Expiration date")
        .setNillable(true)
        .setDescription("When expiration date is null it will never expire");
    addAttribute(CREATIONDATE)
        .setDataType(DATE_TIME)
        .setLabel("Creation date")
        .setAuto(true)
        .setReadOnly(true)
        .setNillable(false);
    addAttribute(DESCRIPTION).setDataType(TEXT).setLabel("Description").setNillable(true);
  }
}
