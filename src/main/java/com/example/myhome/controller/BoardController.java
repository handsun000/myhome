package com.example.myhome.controller;

import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.service.BoardService;
import com.example.myhome.validator.BoardValidatorContent;
import com.example.myhome.validator.BoardValidatorTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {


    final private BoardRepository boardRepository;

    final private BoardValidatorContent boardValidatorContent;

    final private BoardValidatorTitle boardValidatorTitle;

    final private BoardService boardService;

    public BoardController(BoardRepository boardRepository, BoardValidatorContent boardValidatorContent, BoardValidatorTitle boardValidatorTitle, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardValidatorContent = boardValidatorContent;
        this.boardValidatorTitle = boardValidatorTitle;
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() +4 );
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);

        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidatorContent.validate(board, bindingResult);
        boardValidatorTitle.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username, board);
//        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
