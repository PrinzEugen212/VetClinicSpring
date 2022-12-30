package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.models.Employee;
import ru.martynovevgeniy.vetclinic.models.Visit;
import ru.martynovevgeniy.vetclinic.modelsForLayout.Report;
import ru.martynovevgeniy.vetclinic.services.EmployeeService;
import ru.martynovevgeniy.vetclinic.services.VisitService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final VisitService visitService;
    private final EmployeeService employeeService;

    public ReportController(VisitService visitService, EmployeeService employeeService) {
        this.visitService = visitService;
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String reportGet(Model model) {
        model.addAttribute("employees", employeeService.list());
        model.addAttribute("report", new Report());
        return "report/chooseEmployee";
    }

    @GetMapping("/create/chooseEmployee/{employeeId}")
    public String createChooseEmployee(Model model, @PathVariable("employeeId") Long employeeId) {
        Employee employee = null;
        try {
            employee = employeeService.getById(employeeId);
        } catch (Exception exp) {
            return "redirect:/report";
        }

        Report report = new Report();
        report.setEmployee(employee);
        model.addAttribute("report", report);
        return "report/report";
    }

    @PostMapping("/create")
    public String reportPost(Model model, @ModelAttribute("report") @Valid Report report, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "report/report";
        }
        if (report.getStartDate().isAfter(report.getEndDate())) {
            bindingResult.addError(new FieldError(
                    "report", "startDate",
                    report.getStartDate(),
                    false, null, null,
                    "Дата начала не может быть больше даты окончания")
            );
            return "report/report";
        }

        List<Visit> listVisits = visitService.list();

        listVisits.removeIf(value -> value.getDate().isBefore(report.getStartDate()) || value.getDate().isAfter(report.getEndDate()));
        int countVisits = listVisits.size();
        int sumVisitsCost = 0;
        for (var item : listVisits) {
            sumVisitsCost += item.getTotalCost();
        }

        model.addAttribute("report", report);
        model.addAttribute("countVisits", countVisits);
        model.addAttribute("sumVisitsCost", sumVisitsCost);
        return "report/report";
    }
}
