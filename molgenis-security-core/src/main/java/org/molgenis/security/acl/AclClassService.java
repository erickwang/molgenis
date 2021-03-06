package org.molgenis.security.acl;

import java.util.Collection;
import org.springframework.security.acls.model.Acl;

/** Provides retrieval of {@link Acl} classes. */
public interface AclClassService {
  /**
   * Returns whether an ACL class exists for the given type.
   *
   * @param type domain object type
   * @return <tt>true</tt> if an ACL class exists for the given true
   */
  boolean hasAclClass(String type);

  /** Returns all ACL class types. */
  Collection<String> getAclClassTypes();
}
