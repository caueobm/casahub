package casahubBackend.repository;


import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelInteresseRepository extends JpaRepository<ImovelInteresse, Long> {

    List<ImovelInteresse> findByUsuario(Usuario usuario); // Interesses de um usuário

    List<ImovelInteresse> findByImovel(Imovel imovel); // Interesses por um imóvel

    List<ImovelInteresse> findByImovelIn(List<Imovel> imoveis);

    boolean existsByUsuarioAndImovel(Usuario usuario, Imovel imovel); // Se já existe interesse

    @Query("SELECT i FROM ImovelInteresse i " +
            "JOIN FETCH i.imovel imovel " +
            "JOIN FETCH i.usuario usuario " +
            "WHERE i.imovel IN :imoveis")
    List<ImovelInteresse> findByImovelInWithFetch(@Param("imoveis") List<Imovel> imoveis);

}
