package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> getRoleByDescription(String description);
}
