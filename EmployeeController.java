package ta.sb01;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @RequestMapping(value = "/showEmployee/{id}")
    public ModelAndView showEmployee(@PathVariable int id) {
        ModelAndView mv = new ModelAndView();
        Employee e = service.getEmpById(id);

        mv.addObject("emp", e);
        mv.setViewName("EmpDetails");

        return mv;
    }

    @RequestMapping(value = "/showEmployeeWithName/{name}")
    public ModelAndView showEmployee(@PathVariable String name) {
        ModelAndView mv = new ModelAndView();
        Employee e = service.getEmpByName(name);

        mv.addObject("emp", e);
        mv.setViewName("EmpDetails");

        return mv;
    }

    @GetMapping("/showEmployeeList")
    public String showEmployeeList(Model model) {
        List<Employee> empList = service.getEmpList();
        model.addAttribute("empList", empList);
        return "EmpList";
    }

    @GetMapping("/empMinId")
    public String empMinId(Model model) {
        int id=service.getMinEmpId();
        model.addAttribute("message","Min id = "+id);
        return "Result";
    }

    @GetMapping("/empMaxId")
    public String empMaxId(Model model) {
        int id=service.getMaxEmpId();
        model.addAttribute("message","Max id = "+id);
        return "Result";
    }
    @GetMapping("/empForm")
    public String empForm(@ModelAttribute Employee e) {
        e.setId(1);
        e.setName("Duplicate");
        e.setSalary(999);
        return "EmpForm";
    }

        @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute @Valid Employee e, Model model, BindingResult br){
        Employee savedEmp=service.saveEmp(e);
//        Employee savedEmp=service.saveEmpTxn1(e);
        model.addAttribute("emp",savedEmp);
        model.addAttribute("message","Saved emp id="+savedEmp.getId());
        return "EmpDetails";
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleException(MethodArgumentNotValidException e,Model model,BindingResult br) {
        System.out.println("*** handleException(MethodArgumentNotValidException,model) ");
        /*e.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(fieldError -> model.addAttribute(fieldError.getField(), fieldError.getDefaultMessage()));*/

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
//        errors.stream().map(e->e.getField()+e.getDefaultMessage());
        model.addAttribute("errors",errors);
        return "Result";
    }
        @ExceptionHandler(Exception.class)
    public String handleException(Exception e,Model model, BindingResult br){
        System.out.println("*** handleException(Exception,model) ");
        //model.addAttribute("message","crossed the count limit");
        e.printStackTrace();
        model.addAttribute("exceptionClass","Exception class : "+e.getClass());
        model.addAttribute("message","Exception message :"+e.getMessage());
        return "Result";
    }
}