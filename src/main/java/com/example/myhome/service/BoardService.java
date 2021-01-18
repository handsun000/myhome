package com.example.myhome.service;

import com.example.myhome.model.Board;
import com.example.myhome.model.User;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    final private BoardRepository boardRepository;

    final private UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
