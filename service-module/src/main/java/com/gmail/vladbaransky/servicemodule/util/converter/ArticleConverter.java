package com.gmail.vladbaransky.servicemodule.util.converter;

import com.gmail.vladbaransky.repositorymodule.model.Article;
import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
import com.gmail.vladbaransky.servicemodule.model.CommentDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class ArticleConverter {
    public static Article getObjectFromDTO(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setDate(articleDTO.getDate());
        article.setTitle(articleDTO.getTitle());
        article.setSummary(articleDTO.getSummary());
        article.setContent(articleDTO.getContent());

        if (articleDTO.getUserDTO() != null) {
            User user = new User();
            user.setId(articleDTO.getUserDTO().getId());
            user.setName(articleDTO.getUserDTO().getName());
            user.setSurname(articleDTO.getUserDTO().getSurname());
            user.setPatronymic(articleDTO.getUserDTO().getPatronymic());
            user.setUsername(articleDTO.getUserDTO().getUsername());
            user.setPassword(articleDTO.getUserDTO().getPassword());
            user.setRole(articleDTO.getUserDTO().getRole());
            user.setAddress(articleDTO.getUserDTO().getAddress());
            user.setTelephone(articleDTO.getUserDTO().getTelephone());
            article.setUser(user);
        }
        return article;
    }


    public static ArticleDTO getDTOFromObject(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setDate(article.getDate());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setSummary(article.getSummary());
        articleDTO.setContent(article.getContent());

        if (article.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(article.getUser().getId());
            userDTO.setName(article.getUser().getName());
            userDTO.setSurname(article.getUser().getSurname());
            userDTO.setPatronymic(article.getUser().getPatronymic());
            userDTO.setUsername(article.getUser().getUsername());
            userDTO.setPassword(article.getUser().getPassword());
            userDTO.setRole(article.getUser().getRole());
            userDTO.setAddress(article.getUser().getAddress());
            userDTO.setTelephone(article.getUser().getTelephone());
            articleDTO.setUserDTO(userDTO);
        }

        if (article.getComments() != null) {
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (int i = 0; i < article.getComments().size(); i++) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(article.getComments().get(i).getId());
                commentDTO.setDate(article.getComments().get(i).getDate());
                commentDTO.setComment(article.getComments().get(i).getComment());

                if (article.getComments().get(i).getUser() != null) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(article.getComments().get(i).getUser().getId());
                    userDTO.setName(article.getComments().get(i).getUser().getName());
                    userDTO.setSurname(article.getComments().get(i).getUser().getSurname());
                    userDTO.setPatronymic(article.getComments().get(i).getUser().getPatronymic());
                    userDTO.setUsername(article.getComments().get(i).getUser().getUsername());
                    userDTO.setPassword(article.getComments().get(i).getUser().getPassword());
                    userDTO.setRole(article.getComments().get(i).getUser().getRole());
                    userDTO.setAddress(article.getComments().get(i).getUser().getAddress());
                    userDTO.setTelephone(article.getComments().get(i).getUser().getTelephone());
                    commentDTO.setUserDTO(userDTO);
                }
                commentDTOList.add(commentDTO);
            }
            articleDTO.setCommentDTOList(commentDTOList);
        }
        return articleDTO;
    }
}
