package org.joaco;

import java.util.*;
import java.util.stream.Collectors;

public class Main {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        List<Producto> list = cargarProductos();
        System.out.println(list);
        do{
            System.out.println("\n=== Menú de Gestión de Inventario ===");
            System.out.println("1. Filtrado y Transformación");
            System.out.println("2. Reducción de Datos");
            System.out.println("3. Producto más caro");
            System.out.println("4. Uso de Optionals");
            System.out.println("5. Producto más barato");
            System.out.println("6. Productos en stock ordenados alfabéticamente");
            System.out.println("7. Cálculo de Stock Total");
            System.out.println("8. Stock por Categoría");
            System.out.println("9. Aplicar descuento");
            System.out.println("10. Ganancia total inventario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
               case 1 -> filtradoTransformacion(list);
               case 2 -> System.out.println(calcularPromedioHogar(list));
               case 3 -> System.out.println(productoMasCaroToMap(list));
               case 4 -> System.out.println(usoOptional(list).orElse("Producto inexisistente"));
               case 5 -> System.out.println(productoMasBarato(list));
               case 6 -> ordenarCalculandoStock(list);
               case 7 -> System.out.println(calculoStockTotal(list));
               case 8 -> System.out.println(stockPorCategoria(list));
               case 9 ->aplicarDescuento(list);
               case 10 ->gananciaTotalInventario(list);
               case 0 -> System.out.println("Bye!");
               default-> System.err.println("Invalid Option");
            }


        }while (opcion != 0);
        scanner.close();
    }

    public static List<Producto> cargarProductos() {
        return List.of(
                new Producto("Laptop", 1500, "Electrónica", 5),
                new Producto("Smartphone", 800, "Electrónica", 10),
                new Producto("Televisor", 1200, "Electrónica", 3),
                new Producto("Heladera", 2000, "Hogar", 2),
                new Producto("Microondas", 500, "Hogar", 8),
                new Producto("Silla", 150, "Muebles", 12),
                new Producto("Mesa", 300, "Muebles", 7),
                new Producto("Zapatillas", 100, "Deportes", 15),
                new Producto("Pelota", 50, "Deportes", 20),
                new Producto("Bicicleta", 500, "Deportes", 5),
                new Producto("Libro", 30, "Librería", 50),
                new Producto("Cuaderno", 10, "Librería", 100),
                new Producto("Lámpara", 80, "Hogar", 30),
                new Producto("Cafetera", 250, "Hogar", 6),
                new Producto("Auriculares", 120, "Electrónica", 14),
                new Producto("Teclado", 90, "Electrónica", 9),
                new Producto("Mouse", 60, "Electrónica", 18),
                new Producto("Monitor", 700, "Electrónica", 4),
                new Producto("Cama", 800, "Muebles", 2),
                new Producto("Sofá", 1000, "Muebles", 3),
                new Producto("Espejo", 120, "Hogar", 12),
                new Producto("Ventilador", 150, "Hogar", 7),
                new Producto("Patines", 180, "Deportes", 5),
                new Producto("Raqueta", 220, "Deportes", 6),
                new Producto("Taza", 15, "Hogar", 40)
        );
    }
    public static void filtradoTransformacion(List<Producto> list){
        list.stream()
                .filter(x-> x.getCategoria().equals("Electrónica") && x.getPrecio() >= 1000)
                .sorted((x,y)-> Double.compare(y.getPrecio(), x.getPrecio()))
                .forEach(System.out::println);
    }

    public static Double calcularPromedioHogar(List<Producto> list)
    {
      return   list.stream()
              .filter(x-> x.getCategoria().equals("Hogar") && x.getStock()>0)
              .mapToDouble(Producto::getPrecio)
              .average().orElse(0);
    }

    public static Map<String,Producto> productoMasCaroToMap(List<Producto> list)
    {
        return list.stream()
                .collect(
                        Collectors
                                .toMap(Producto::getCategoria,
                                        p-> p,
                                        (p1,p2) -> p1.getPrecio()>p2.getPrecio() ? p1 : p2
                                ));
    }



    public static Optional<String> usoOptional(List<Producto> list) {
        return list.stream()
                .filter(x-> x.getCategoria().equals("Deportes") && x.getStock()>10)
                .map(x-> x.getNombre().toLowerCase())
                .findFirst();

    }

    public static Producto productoMasBarato(List<Producto> list)
    {
       return list.stream()
                .min(Comparator.comparingDouble(p1-> p1.getPrecio()*p1.getStock() ))
               .orElseThrow(()->new NoSuchElementException("Elemento no encontrado"));
    }

    public static void ordenarCalculandoStock(List <Producto> list)
    {
        list.stream()
                .filter(p1-> p1.getStock()>0 && p1.getNombre().length()> 4)
                .map(Producto::getNombre)
                .sorted()
                .forEach(System.out::println);
    }

    public static Integer calculoStockTotal(List<Producto> list) {
        Double promedio = list.stream()
                .mapToDouble(Producto::getPrecio)
                .average().orElse(0);
        System.out.println(promedio);
        return list.stream()
                .filter(p1-> p1.getPrecio()>promedio)
                .map(Producto::getStock)
                .reduce(Integer::sum).orElse(0);

    }

    public static Map<String, Integer> stockPorCategoria(List<Producto> list)
    {
        Map<String ,Long> map= list.stream()
                .collect(
                        Collectors
                                .groupingBy(Producto::getCategoria,Collectors.counting()));
       return list.stream()
                .filter(producto -> map.get(producto.getCategoria()) > 3)
                .collect(Collectors.groupingBy(Producto::getCategoria,Collectors.summingInt(Producto::getStock)));
    }

    public static void aplicarDescuento(List <Producto> list)
    {
        list.stream()
                .filter(p1-> p1.getStock()>20)
                .map(n -> new Producto(n.getNombre(), n.getPrecio() * 0.85, n.getCategoria(), n.getStock()))
                .forEach(System.out::println);
    }

    public static void gananciaTotalInventario(List<Producto> list)
    {
       double aDouble = list.stream()
                .map(
                        p1-> p1.getCategoria().equals("Electrónica") ?
                        new Producto(p1,p1.getPrecio()-p1.getPrecio()*0.65) :
                                new Producto(p1,p1.getPrecio()-p1.getPrecio()*0.45)
                    )
                .map(Producto::getPrecio)
                .reduce(Double::sum).orElse(0.00);
        System.out.println(aDouble);
    }
}