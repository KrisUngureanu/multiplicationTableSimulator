package kz.kris.trenazher.controller;

import kz.kris.trenazher.model.Primer;
import kz.kris.trenazher.service.PrimerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final PrimerService primerService;
    public HomeController(PrimerService primerService) {
        this.primerService = primerService;
    }


    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("/reshenie/{param}")
    public String resheniePage(@PathVariable String param, Model model){
        List<Primer> primerList = null;
        List<Primer> multiplierList = null;
        List<Primer> dividerList = null;
        List<Primer> multiplierListObrat = null;
        List<Primer> dividerListObrat = null;
        if (param.equals("all")) {
            primerList = primerService.give4Primer();
            multiplierList = primerList.subList(0, 2);
            dividerList = primerList.subList(2, 4);


            primerList = primerService.give4Primer();
            multiplierListObrat = primerList.subList(0, 2);
            dividerListObrat = primerList.subList(2, 4);
            model.addAttribute("multiplierList", multiplierList);
            model.addAttribute("dividerList", dividerList);

            model.addAttribute("multiplierListObrat", multiplierListObrat);
            model.addAttribute("dividerListObrat", dividerListObrat);

        } else {
            System.out.println(Integer.parseInt(param));
            primerList = primerService.give4PrimerForOperand(Integer.parseInt(param));
            multiplierList = primerList.subList(0, 2);
            dividerList = primerList.subList(2, 4);
            primerList = primerService.give4PrimerForOperand(Integer.parseInt(param));
            multiplierListObrat = primerList.subList(0, 2);
            dividerListObrat = primerList.subList(2, 4);
            model.addAttribute("multiplierList", multiplierList);
            model.addAttribute("dividerList", dividerList);
            model.addAttribute("multiplierListObrat", multiplierListObrat);
            model.addAttribute("dividerListObrat", dividerListObrat);
        }

        model.addAttribute("primers", primerList);
        return "reshenie";
    }



}
