package ma.enset.patientmvc.sec.service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.enset.patientmvc.sec.entities.AppRole;
import ma.enset.patientmvc.sec.entities.AppUser;
import ma.enset.patientmvc.sec.repositories.AppRoleRepository;
import ma.enset.patientmvc.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String verifyPwd) {
        if(!password.equals(verifyPwd)) throw new RuntimeException("Passwords not match");
        String hashedPwd = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPwd);
        appUser.setActive(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole!=null) throw new RuntimeException("Role "+roleName+" already exist");
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        appRoleRepository.save(appRole);
        return appRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role not found");
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser==null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole==null) throw new RuntimeException("Role not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String username) {

        return appUserRepository.findByUsername(username);
    }
}
