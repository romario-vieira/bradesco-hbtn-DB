package mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class UsuarioOperations {

	public static void main(String[] args) {
		MongoDBConnection connection = new MongoDBConnection();
		
		MongoCollection<Document> col = connection.getDatabase().getCollection("testeMongo");
		
		Usuario usuario1 = new Usuario("Alice", 25);
		Usuario usuario2 = new Usuario("Bob", 30);
		Usuario usuario3 = new Usuario("Charlie", 35);
		
		List<Document> docs = new ArrayList<>();
		docs.add(usuario2.toDocument());
		docs.add(usuario3.toDocument());
		
		col.insertOne(usuario1.toDocument());
		col.insertMany(docs);
		System.out.println("Usuarios incluidos com sucesso!!");
		
		
		col.find().forEach( n -> System.out.println("Registro encontrado: " + n.toJson()));
		
		
		Bson filtro = Filters.eq("nome", "Bob");
		
		Bson atualizacoes = Updates.combine(
					Updates.set("idade", 32)
				);
		
		col.updateOne(filtro, atualizacoes);
		
		col.find().forEach( n -> System.out.println("Registro encontrado pos atualização: " + n.toJson()));
		
		Bson filtroDelete = Filters.eq("nome", "Charlie");
		col.deleteOne(filtroDelete);
		
		col.find().forEach( n -> System.out.println("Registro encontrado pos delete: " + n.toJson()));
		
	}
}
