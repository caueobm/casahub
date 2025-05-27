package casahubBackend.repository;


import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelInteresseRepository extends JpaRepository<ImovelInteresse, Long> {

    List<ImovelInteresse> findByUsuario(Usuario usuario); // Interesses de um usuário

    List<ImovelInteresse> findByImovel(Imovel imovel); // Interesses por um imóvel

    boolean existsByUsuarioAndImovel(Usuario usuario, Imovel imovel); // Se já existe interesse
}
