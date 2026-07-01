package com.projeto.e_commerce.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.order.enums.OrderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
// o @Data deu problema com addItem
// @Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    // O cascade diz: "Se eu salvar o Pedido, salve os itens da lista automaticamente"
    // O orphanRemoval diz: "Se eu tirar um item da lista, delete ele do banco"
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItens> itens = new ArrayList<>();

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // serve para amarrar os dois lados da relação no Java
    public void addItem(OrderItens item) {
        // pega todos os itens passados no itens e manda para o list
        // e depois vai ser armazenado no set do orderItens, indo diredo para o entity OrderItens
        item.setOrder(this);
        this.itens.add(item);
    }
}
