package ut5_biblioteca_db4o_2425;

import com.db4o.*;
import com.db4o.query.Constraint;
import com.db4o.query.Query;

public class UT5_biblioteca_db4o_2425 {


    public static void main(String[] args) {
        
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "biblioteca.db40");
        
        try{
            borrarDatos(db);
            insertarDatos(db);
            consultasQBE(db);
            consultasSoda(db);
            
            
        }finally{
            db.close();
        }
    }
  
    private static void insertarDatos(ObjectContainer base) {
        
        Autores a1 = new Autores("Murakami", "Japón");
        Autores a2 = new Autores("Gabo","Colombia");
        
        Libros l1 = new Libros("1084", "Fantastica", 1034, a1);
        Libros l2 = new Libros("Tokio BLues", "Fantastica", 345, a1);
        Libros l3 = new Libros("Cien anios de soledad","Realismo magico",654,a2);
        Libros l4 = new Libros("El coronel no tiene quien le escriba","Amor", 554,a2);
        
        base.store(l1);
        base.store(l2);
        base.store(l3);
        base.store(l4);
        
    }
    
    private static void consultasQBE(ObjectContainer base){
        
        System.out.println("\nConsultas QBE\n");
        Autores a = new Autores (null );
        ObjectSet res = base.queryByExample(a);
        while (res.hasNext()){
            System.out.println(res.next());
        }
    }
        
    private static void consultasSoda(ObjectContainer base){
        
        System.out.println("\nConsultas Soda\n");
        
        Query query = base.query();
        Query query2 = base.query();
        Query query3 = base.query();
        query.constrain(Libros.class);
        ObjectSet res;
        
        System.out.println("\nConsultas Soda, libros con menos de 600 páginas\n");
        query.descend("num_pag").constrain(600).smaller();
        res=query.execute();
        while (res.hasNext()){
            System.out.println(res.next());
        }
        
        System.out.println("\nConsultas Soda, libros con número de páginas entre 500 y 1000");
        Constraint cons2=query2.descend("num_pag").constrain(1000).smaller();
        query2.descend("num_pag").constrain(500).greater().and(cons2);
        res=query2.execute();
        while (res.hasNext()){
            System.out.println(res.next());
        }
        
        System.out.println("\nConsultas Soda, libros con una w o una l\n");
        Constraint cons3=query3.descend("titulo").constrain("w").like();
        query3.descend("titulo").constrain("l").like().or(cons3);
        res=query3.execute();
        while (res.hasNext()){
            System.out.println(res.next());
        }
    }
    
    private static void borrarDatos(ObjectContainer base){
        
        Libros l = new Libros(null, null, 0, null);
        ObjectSet res = base.queryByExample(l);
        while (res.hasNext()){
            base.delete(res.next());
        }
        
        Autores a = new Autores(null, null);
        res = base.queryByExample(a);
        while (res.hasNext()){
            base.delete(res.next());
        
        }      
    }
}
