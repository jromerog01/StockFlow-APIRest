package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.repositories.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DatabaseSeederService {

    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final VentaProductoRepository ventaProductoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public DatabaseSeederService(CategoriaRepository categoriaRepository,
                                 ProveedorRepository proveedorRepository,
                                 ProductoRepository productoRepository,
                                 VentaRepository ventaRepository,
                                 VentaProductoRepository ventaProductoRepository,
                                 MovimientoInventarioRepository movimientoInventarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
        this.ventaProductoRepository = ventaProductoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    // Cron = 0 0 3 * * ? significa "a las 3:00 AM todos los días"
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void seedDatabase() {
        System.out.println("Iniciando reseteo de base de datos con datos semilla...");

        // 1. Borrar datos existentes (en orden inverso de dependencias)
        ventaProductoRepository.deleteAll();
        ventaRepository.deleteAll();
        movimientoInventarioRepository.deleteAll();
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();
        proveedorRepository.deleteAll();

        // 2. Crear Categorías
        Categoria catElectronica = categoriaRepository.save(new Categoria("Electrónica"));
        Categoria catHogar = categoriaRepository.save(new Categoria("Hogar"));
        Categoria catDeportes = categoriaRepository.save(new Categoria("Deportes"));
        Categoria catRopa = categoriaRepository.save(new Categoria("Ropa"));
        Categoria catJuguetes = categoriaRepository.save(new Categoria("Juguetes"));

        // 3. Crear Proveedores
        Proveedor provTech = proveedorRepository.save(new Proveedor("TechCorp S.A.", "5551234567", "contacto@techcorp.com"));
        Proveedor provHome = proveedorRepository.save(new Proveedor("HomeEssentials", "5555678901", "ventas@homeessentials.com"));
        Proveedor provFashion = proveedorRepository.save(new Proveedor("Moda Express", "5559012345", "hola@modaexpress.com"));
        Proveedor provKids = proveedorRepository.save(new Proveedor("Mundo Juguete", "5553456789", "info@mundojuguete.com"));

        // 4. Crear Productos
        Iterable<Producto> productosIterable = productoRepository.saveAll(List.of(
                // Electrónica
                new Producto(catElectronica, provTech, "LAP-001", "Laptop Pro X", new BigDecimal("25000.00"), 10),
                new Producto(catElectronica, provTech, "CEL-002", "Smartphone Ultra", new BigDecimal("15000.00"), 25),
                new Producto(catElectronica, provTech, "AUD-003", "Audífonos Inalámbricos", new BigDecimal("1200.00"), 50),
                new Producto(catElectronica, provTech, "MON-004", "Monitor 4K 27\"", new BigDecimal("7500.00"), 15),
                // Hogar
                new Producto(catHogar, provHome, "MUE-001", "Sofá de Cuero", new BigDecimal("8500.00"), 5),
                new Producto(catHogar, provHome, "COM-002", "Comedor 6 sillas", new BigDecimal("12000.00"), 3),
                new Producto(catHogar, provHome, "LAV-003", "Lavadora Automática", new BigDecimal("9500.00"), 8),
                // Deportes
                new Producto(catDeportes, provTech, "BIC-001", "Bicicleta de Montaña", new BigDecimal("12000.00"), 8),
                new Producto(catDeportes, provTech, "MAN-002", "Mancuernas 10kg", new BigDecimal("800.00"), 30),
                new Producto(catDeportes, provTech, "TAP-003", "Tapete de Yoga", new BigDecimal("350.00"), 40),
                // Ropa
                new Producto(catRopa, provFashion, "CAM-001", "Camisa de Algodón", new BigDecimal("450.00"), 100),
                new Producto(catRopa, provFashion, "PAN-002", "Pantalón de Mezclilla", new BigDecimal("700.00"), 80),
                new Producto(catRopa, provFashion, "CHA-003", "Chamarra de Piel", new BigDecimal("2500.00"), 20),
                // Juguetes
                new Producto(catJuguetes, provKids, "FIG-001", "Figura de Acción", new BigDecimal("300.00"), 150),
                new Producto(catJuguetes, provKids, "ROM-002", "Rompecabezas 1000 pz", new BigDecimal("250.00"), 60),
                new Producto(catJuguetes, provKids, "CON-003", "Consola Retro", new BigDecimal("1500.00"), 40)
        ));

        List<Producto> productosGuardados = new java.util.ArrayList<>();
        productosIterable.forEach(productosGuardados::add);

        // Asignamos algunos productos a variables por comodidad
        Producto laptop = productosGuardados.get(0);
        Producto audifonos = productosGuardados.get(2);
        Producto sofa = productosGuardados.get(4);
        Producto lavadora = productosGuardados.get(6);
        Producto bicicleta = productosGuardados.get(7);

        // 5. Crear Movimientos de Inventario
        movimientoInventarioRepository.saveAll(List.of(
                new MovimientoInventario(laptop, TipoMovimiento.ENTRADA, 10),
                new MovimientoInventario(laptop, TipoMovimiento.SALIDA, 1), // Se vendió 1
                new MovimientoInventario(audifonos, TipoMovimiento.ENTRADA, 50),
                new MovimientoInventario(audifonos, TipoMovimiento.SALIDA, 2), // Se vendieron 2
                new MovimientoInventario(sofa, TipoMovimiento.ENTRADA, 5),
                new MovimientoInventario(sofa, TipoMovimiento.SALIDA, 1),
                new MovimientoInventario(lavadora, TipoMovimiento.ENTRADA, 8),
                new MovimientoInventario(lavadora, TipoMovimiento.SALIDA, 1),
                new MovimientoInventario(bicicleta, TipoMovimiento.ENTRADA, 8)
        ));

        // 6. Crear Ventas
        // Venta 1: 1 Laptop (25,000) -> Subtotal = 25,000, Total = 29,000 (+16% IVA)
        Venta venta1 = ventaRepository.save(new Venta(MetodoPago.TARJETA, new BigDecimal("25000.00"), new BigDecimal("29000.00")));
        
        // Venta 2: 2 Audífonos (1,200 c/u) -> Subtotal = 2,400, Total = 2,784 (+16% IVA)
        Venta venta2 = ventaRepository.save(new Venta(MetodoPago.EFECTIVO, new BigDecimal("2400.00"), new BigDecimal("2784.00")));
        
        // Venta 3: 1 Sofá (8,500) + 1 Lavadora (9,500) -> Subtotal = 18,000, Total = 20,880 (+16% IVA)
        Venta venta3 = ventaRepository.save(new Venta(MetodoPago.TARJETA, new BigDecimal("18000.00"), new BigDecimal("20880.00")));

        // 7. Crear Detalles de Ventas (VentaProducto)
        ventaProductoRepository.saveAll(List.of(
                // Detalles Venta 1
                new VentaProducto(laptop, venta1, 1, new BigDecimal("25000.00"), new BigDecimal("25000.00")),
                // Detalles Venta 2
                new VentaProducto(audifonos, venta2, 2, new BigDecimal("1200.00"), new BigDecimal("2400.00")),
                // Detalles Venta 3
                new VentaProducto(sofa, venta3, 1, new BigDecimal("8500.00"), new BigDecimal("8500.00")),
                new VentaProducto(lavadora, venta3, 1, new BigDecimal("9500.00"), new BigDecimal("9500.00"))
        ));

        System.out.println("Reseteo de base de datos completado con éxito (Incluyendo Movimientos y Ventas).");
    }
}
