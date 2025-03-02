//package com.drscan.web.Controller;
//
//import com.drscan.web.primary.users.util.ResponseDto;
//import com.drscan.web.secondary.patientScan.domain.Patient;
//import com.drscan.web.secondary.patientScan.service.PatientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@RequestMapping("patients/action/")
//@RestController
//public class PatientRestController {
//
//    private final PatientService patientService;
//
//    @GetMapping("/all")
//    public ResponseEntity<?> getPatientAll(Pageable pageable, @RequestParam(required = false)Integer page){
//        Pageable paging = PageRequest.of(page == null ? 0 : page - 1, 5, Sort.by(Sort.Direction.DESC, "regDate"));
//
//        List<Patient> list = patientService.findAll(paging);
//
//        int totalPages = patientService.getTotalPages(paging);
//
//        if(list.isEmpty())
//            return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
//                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 페이지입니다."));
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("patients", list);
//        data.put("totalPages", totalPages);
//
//        return ResponseEntity.ok(data);
//    }
//
//    @GetMapping("/detail/{code}")
//    public ResponseEntity<ResponseDto> getPatientByCode(@PathVariable String code) {
//        Patient patient = PatientService.findPatientByPid(code);
//
//        if(board == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND.value())
//                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "존재하지 않는 게시물입니다."));
//        }
//
//        BoardResponseDto boardDto = new BoardResponseDto(board);
//        return ResponseEntity.ok(boardDto);
//    }
//}
