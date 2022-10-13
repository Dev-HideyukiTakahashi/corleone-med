package br.com.corleone.medlife.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.corleone.medlife.model.entities.Paciente;
import br.com.corleone.medlife.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

  private final PacienteRepository pacienteRepository;

  @GetMapping
  public ModelAndView pacientesView() {
    ModelAndView mv = new ModelAndView("/pacientes/lista-pacientes");
    mv.addObject("pacientes", pacienteRepository.findAll());
    return mv;

  }

  @GetMapping(path = "/buscar")
  public ModelAndView buscarPorNome(@RequestParam(name = "nome") String nome) {
    List<Paciente> pacientes = pacienteRepository.findByNomeContainsIgnoreCase(nome);
    ModelAndView mv = new ModelAndView("/pacientes/lista-pacientes");
    if (pacientes.size() < 1) {
      mv.addObject("pacientes", null);
      mv.addObject("registros", "Sem registros de pacientes.");
    } else {
      mv.addObject("registros", pacientes.size() + " registros em sistema");
      mv.addObject("pacientes", pacientes);
    }

    return mv;
  }

  @GetMapping(path = "/salvar")
  public ModelAndView salvar() {
    ModelAndView mv = new ModelAndView("/pacientes/form-pacientes");
    mv.addObject("paciente", new Paciente());
    return mv;
  }

  @GetMapping(path = "/editar/{id}")
  public ModelAndView editar(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("/pacientes/form-pacientes");
    Paciente paciente = pacienteRepository.findById(id).get();

    String data = (paciente.getDataNascimento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    mv.addObject("paciente", paciente);
    mv.addObject("data", data);
    return mv;
  }

  @PostMapping(path = "/salvar")
  public ModelAndView salvar(@Valid Paciente paciente, BindingResult result) {
    ModelAndView mv = new ModelAndView("/pacientes/form-pacientes");

    if (result.hasErrors()) {
      return mv;
    }

    if (pacienteRepository.existsByCpf(paciente.getCpf()) && paciente.getId() == null) {
      mv.addObject("erro", "Já existe um usuário com esse CPF");
      return mv;
    } else {
      mv.setViewName("redirect:/pacientes");
      pacienteRepository.save(paciente);
      return mv;
    }
  }

}
