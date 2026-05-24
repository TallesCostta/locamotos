package br.com.donatti.locamotos.moto.service;

import br.com.donatti.locamotos.moto.exception.MotoException;
import br.com.donatti.locamotos.moto.model.Moto;
import br.com.donatti.locamotos.moto.repository.MotoRepository;
import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotoService
{
    private final MotoRepository motoRepository;

    public List<Moto> listarTodasMotos()
    {
        return motoRepository.findAll();
    }

    public Optional<Moto> buscarMotoPorId(final Long id)
    {
        if (Objects.isNull(id))
        {
            throw new MotoException("Id inválido");
        }
        return motoRepository.findById(id);
    }

    public List<Moto> buscarMotoPorModelo(final String modelo)
    {
        if (StringUtil.isNullOrEmpty(modelo))
        {
            throw new MotoException("Modelo inválido");
        }
        return motoRepository.findByModeloContainingIgnoreCase(modelo);
    }

    public Moto salvarMoto(final Moto moto)
    {
        validarCampos(moto);
        if (Objects.nonNull(motoRepository.findByPlacaContainingIgnoreCase(moto.getPlaca())))
        {
            throw new MotoException("Moto não pode ser salva pois já existe na base de dados!");
        }
        return motoRepository.save(moto);
    }

    private void validarCampos(Moto moto)
    {
        if (Objects.isNull(moto))
        {
            throw new MotoException("Moto inválida");
        }
        else if (StringUtil.isNullOrEmpty(moto.getPlaca()))
        {
            throw new MotoException("Placa inválida");
        }
        else if (StringUtil.isNullOrEmpty(moto.getModelo()))
        {
            throw new MotoException("Modelo inválido");
        }
    }

    public void deletarMoto(final Long id)
    {
        if (!motoRepository.existsById(id))
        {
            throw new MotoException("Moto não encontrada");
        }
        motoRepository.deleteById(id);
    }

    public long contarMotosInventario()
    {
        return motoRepository.count();
    }
}