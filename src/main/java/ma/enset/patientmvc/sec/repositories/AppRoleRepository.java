package ma.enset.patientmvc.sec.repositories;

import ma.enset.patientmvc.sec.entities.AppRole;
import ma.enset.patientmvc.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
    
}
