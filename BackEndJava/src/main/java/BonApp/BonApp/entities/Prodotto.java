package BonApp.BonApp.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import BonApp.BonApp.Enum.Categoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prodotto")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Prodotto {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
  
    @ManyToMany (cascade = {CascadeType.MERGE})
    @JoinTable(
      name = "prodotto_ingrediente", 
      joinColumns = @JoinColumn(name = "prodotto_id"), 
      inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredienti = new ArrayList<>();
    
    @JsonIgnore
    //@ManyToMany(mappedBy = "prodotti")
    @ManyToMany(mappedBy = "prodotti", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OrdineSingolo> orders = new ArrayList<>();
    
   @JsonIgnore
    @ManyToMany(mappedBy = "prodottiPreferiti")
    private List<User> usersFavouriteProducts = new ArrayList<>();

    
    
    public Prodotto(String nome, String descrizione, double prezzo, Categoria categoria, List<Ingrediente> ingredienti, String imgUrl) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.ingredienti = ingredienti;
        this.imgUrl = imgUrl;
    }
    
    
}
