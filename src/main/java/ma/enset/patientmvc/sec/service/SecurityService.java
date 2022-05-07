package ma.enset.patientmvc.sec.service;

import ma.enset.patientmvc.sec.entities.AppRole;
import ma.enset.patientmvc.sec.entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String verifyPwd);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFromUser(String username,String roleName);
    AppUser loadUserByUserName(String username);
}
