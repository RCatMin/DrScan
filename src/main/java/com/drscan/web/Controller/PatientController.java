//package com.drscan.web.Controller;
//
//import com.drscan.web.secondary.patientScan.domain.Patient;
//import com.drscan.web.secondary.patientScan.domain.PatientRepository;
//import com.drscan.web.secondary.patientScan.service.PatientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RequestMapping("/patients")
//@Controller
//public class PatientController {
//   private final PatientRepository patientRepository;
//    private final PatientService patientService;
//
//    @GetMapping({"","/"})
//    public ModelAndView list(Pageable pageable, @RequestParam(required = false) Integer page) {
//        ModelAndView modelAndView = new ModelAndView("patient/list");
//
//        Pageable paging = PageRequest.of(page ==null ? 0:page-1,5);
//
//        List<Patient> patients = patientService.findAll(paging);
//        modelAndView.addObject("patients", patients);
//
//        int totalPages = patientService.getTotalPages(paging);
//        modelAndView.addObject("totalPages", totalPages);
//
//        return modelAndView;
//    }
//}
