package br.com.mkcf.personapi.convert;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperConvert {

    private static ModelMapper modelMapper = new ModelMapper();

    public static <O, D> D parseObject (O origin, Class<D> destination){
        return modelMapper.map(origin,destination);
    }

    public static <O, D> List<D> parseListObjects (List<O> origin, Class<D> destination){
        List<D> destionationObjects = new ArrayList<D>();
        return origin.stream().map(o -> modelMapper.map(o, destination)).collect(Collectors.toList());
    }

}
