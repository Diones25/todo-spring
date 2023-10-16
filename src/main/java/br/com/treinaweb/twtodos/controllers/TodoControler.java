package br.com.treinaweb.twtodos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twtodos.models.Todo;
import br.com.treinaweb.twtodos.repositories.TodoRepository;
import jakarta.validation.Valid;

@Controller
public class TodoControler {

  @Autowired
  TodoRepository todoRepository;
  
  @GetMapping("/")
  public ModelAndView list() {
    var modelAndView = new ModelAndView("todo/list");
    modelAndView.addObject("todos", todoRepository.findAll());

    return modelAndView;
  }

  @GetMapping("/create")
  public ModelAndView create() {
    var modelAndView = new ModelAndView("todo/form");
    modelAndView.addObject("todo", new Todo());

    return modelAndView;
  }

  @PostMapping("/create")
  public String create(@Valid Todo todo, BindingResult result) {

    if(result.hasErrors()) {
      return "todo/form";
    }

    todoRepository.save(todo);
    return "redirect:/";
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable Long id) {
    var todo = todoRepository.findById(id);

    if(todo.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    var modelAndView = new ModelAndView("todo/form");
    modelAndView.addObject("todo", todo.get());

    return modelAndView;
  }  

  @PostMapping("/edit/{id}")
  public String edit(@Valid Todo todo, BindingResult result) {

    if(result.hasErrors()) {
      return "todo/form";
    }

    todoRepository.save(todo);
    return "redirect:/";
  }

  @GetMapping("/delete/{id}")
  public ModelAndView delete(@PathVariable Long id) {
    var todo = todoRepository.findById(id);

    if(todo.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    var modelAndView = new ModelAndView("todo/delete");
    modelAndView.addObject("todo", todo.get());

    return modelAndView;
  }

  @PostMapping("/delete/{id}")
  public String delete(Todo todo) {
    todoRepository.delete(todo);
    return "redirect:/";
  }
}
