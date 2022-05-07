package ma.enset.patientmvc.web;

import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PatientController {

    private PatientRepository pr;
    public PatientController(PatientRepository pr) {
        this.pr = pr;
    }
    @GetMapping(path = "/user/index")
    public String patients(Model m,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5")int size,
                           @RequestParam(name = "keyword",defaultValue = "")String keyword){
        Page<Patient> pagePatients = pr.findByNomContains(keyword,PageRequest.of(page,size));
        m.addAttribute("listPatients",pagePatients.getContent());
        m.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        m.addAttribute("currentPage",page);
        m.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping(path = "/admin/delete")
    public String Delete(Long id,String keyword,int page){
        pr.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/")
    public String home(){
        return "home";
    }

    @GetMapping(path = "/admin/formPatients")
    public String formPatients(Model m){
        m.addAttribute("patient", new Patient());

        return "formPatients";
    }
    @RequestMapping(value = "/admin/save",method = RequestMethod.POST)
    public String save(Model m, @Valid Patient p, BindingResult br,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword
                       ){
        if (br.hasErrors()) return "formPatients";
        pr.save(p);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/admin/editPatient")
    public String editPatient(Model m,Long id,String keyword,int page){
        Patient p = pr.findById(id).orElse(null);
        if (p==null) throw new RuntimeException("patient introuvable");
        m.addAttribute("patient", p);
        m.addAttribute("page", page);
        m.addAttribute("keyword", keyword);
        return "editPatient";
    }

}