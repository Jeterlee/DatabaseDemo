package cn.jeterlee.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GreendaoUser {

    @Id
    private Long id; // id Long类型
    private String name; // name
    @Unique
    private Long cardId; // 卡id unique唯一

    @Generated(hash = 1773417249)
    public GreendaoUser(Long id, String name, Long cardId) {
        this.id = id;
        this.name = name;
        this.cardId = cardId;
    }

    @Generated(hash = 13303132)
    public GreendaoUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardId() {
        return this.cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
