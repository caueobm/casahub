package casahubBackend.repository;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    List<Imovel> findByUsuario(Usuario usuario); // Buscar imóveis por corretor

    List<Imovel> findByCidadeAndDisponivelIsTrue(String cidade); // Buscar imóveis disponíveis por cidade

    List<Imovel> findByDisponivelTrue(); // Todos disponíveis

}
