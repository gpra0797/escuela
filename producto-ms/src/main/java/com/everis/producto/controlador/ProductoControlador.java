package com.everis.producto.controlador;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.producto.dto.ProductoDTO;
import com.everis.producto.dto.ProductoReducidoDTO;
import com.everis.producto.entity.ImagenProducto;
import com.everis.producto.entity.Producto;
import com.everis.producto.entity.TipoProducto;
import com.everis.producto.exceptions.ResourceNotFoundException;
import com.everis.producto.exceptions.ValidationException;
import com.everis.producto.service.ProductoService;
import com.everis.producto.service.impl.ProductoServiceImpl;

@RestController
public class ProductoControlador {
	
	@Autowired
	private ProductoServiceImpl productoService;
	
	@GetMapping(value = "/productos")
	public List<ProductoDTO> obtenerProductos() {
		ModelMapper mapper = new ModelMapper();
		Iterable<Producto> productos = productoService.obtenerProductos();
		StreamSupport.stream(productos.spliterator(), false)
					.map(p -> mapper.map(p, ProductoDTO.class))
					.collect(Collectors.toList());
		return null;
	}
	
	@GetMapping(value = "/productos/{id}")
	public ProductoDTO obtenerProducto(
			@PathVariable("id") Long id
			) throws ResourceNotFoundException{
		
		ModelMapper mapper = new ModelMapper();
		return mapper.map(productoService.obtenerProductoXId(id), ProductoDTO.class);
	}
	
	@PostMapping(value = "/productos/{id}")
	public ProductoDTO guardarProducto(
			@RequestBody ProductoReducidoDTO producto
			) throws ValidationException,ResourceNotFoundException {
		ModelMapper mapper = new ModelMapper();
		Producto productoMapper = mapper.map(producto, Producto.class);
		
		TipoProducto tipoProducto = new TipoProducto();
		tipoProducto.setCodigo(producto.getCodigoProducto());
		productoMapper.setTipoProducto(tipoProducto);
		
		ImagenProducto imgProducto = new ImagenProducto();
		imgProducto.setRuta(producto.getRutaImagen());
		imgProducto.setRutaThumbnail(producto.getRutaThumbnail());
		productoMapper.setImagenProducto(imgProducto);
		
		
		return mapper.map(productoService.guardarProducto(productoMapper), ProductoDTO.class);
	}
}
