package com.projeto.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.projeto.store.model.Jogo;
import com.projeto.store.services.JogoService;


@Controller
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService service;

    @Autowired
    PaymentController paymentController;

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Jogo createJogo(@RequestBody Jogo jogo, @RequestParam String image, @RequestParam String nome, @RequestParam String descricao, @RequestParam double preco, @RequestParam String categoria, @RequestParam List<String> plataformList)

{
    if(!verifyAmount(preco)){
        throw new IllegalArgumentException("Invalid amount");
    }

    return service.createJogo(jogo);
}


@GetMapping
public List<Jogo> getJogos(){
    return service.getAllJogos();
}

@GetMapping("/{jogoId}")
public Jogo getJogo(@PathVariable String jogoId){
    return service.getJogoById(jogoId);
}

@GetMapping("/nome/{nome}")
public Jogo findJogoUsingNome(@PathVariable String nome){
    return service.getJogoByNome(nome);
}

@GetMapping("/categoria/{categoria}")
public List<Jogo> findJogoUsingCategoria(@PathVariable String categoria){
    return service.getJogoByCategoria(categoria);
}

@DeleteMapping("/{jogoId}")
public String deleteJogo(@PathVariable String jogoId){
    return service.deleteJogo(jogoId);
}






@GetMapping("/page")
public String getJogosPage(Model model) {
    List<Jogo> jogos = service.getAllJogos();
    model.addAttribute("jogos", jogos); // Adiciona a lista de jogos ao modelo
    return "jogos";
}

private boolean verifyAmount(double preco) {
    return preco > 0;
}







// verificações aqui


@PostMapping("/publish")
public String publishPage(
        @RequestParam String nome,
        @RequestParam String descricao,
        @RequestParam double preco,
        @RequestParam String categoria,
        @RequestParam List<String> plataformList
) {
    // Lógica para publicar a página do jogo

    // Verifique se o jogo já está publicado
    if (service.getJogoByNome(nome) != null) {
        // Redirecione de volta para a página de jogos
        return "redirect:/jogos/page";
    }

    Jogo jogo = new Jogo();
    jogo.setNome(nome);
    jogo.setDescricao(descricao);
    jogo.setPreco(preco);
    jogo.setCategoria(categoria);
    jogo.setPlataformList(plataformList);

    service.createJogo(jogo);


    return "redirect:/jogos/page";
}





@PostMapping("/comprar/{jogoId}")
@ResponseStatus(HttpStatus.CREATED)
public String comprarJogo(@PathVariable String jogoId, Model model) {
    Jogo jogo = service.getJogoById(jogoId);
    
    if (jogo == null) {
        model.addAttribute("error", "Jogo não encontrado");
        return "error";
    }
    
    // Resto do código para processar a compra do jogo
    
    return  "redirect:/comprar/" + jogoId;
}

@GetMapping("/comprar/{jogoId}")
public String comprarJogoPage(@PathVariable String jogoId, Model model) {
    Jogo jogo = service.getJogoById(jogoId);
    
    if (jogo == null) {
        model.addAttribute("error", "Jogo não encontrado");
        return "error";
    }
    
    model.addAttribute("jogo", jogo);
    return "payment";
}




}





