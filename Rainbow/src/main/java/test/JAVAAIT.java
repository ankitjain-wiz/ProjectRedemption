package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JAVAAIT {

	public static void main(String[] args) throws Exception {

		DataType dataType = new DataType();
		Comment commentOne = new Comment();
		Comment commentTwo = new Comment();
		commentOne.setCommentOne("CommentOne");
		commentOne.setCommentTwo("CommentOne");
		commentTwo.setCommentOne("CommentTwo");
		commentTwo.setCommentTwo("CommentTwo");
		List<Comment> commentList = new ArrayList<>();
		commentList.add(commentOne);
		commentList.add(commentTwo);
		dataType.setComment(commentTwo);

		// Map Datatype to comment using optional
		Comment test = Optional.of(dataType).map(DataType::getComment).orElseThrow(() -> new Exception("hi"));
		System.out.println(test);

		// Map Datatype to comment to String using optional
		String commentOneMap = Optional.of(dataType).map(DataType::getComment).map(Comment::getCommentOne)
				.orElseThrow(() -> new Exception("hi"));
		System.out.println(commentOneMap);

		// Map Datatype to comment to String using optional
		String commentTwoMap = Optional.of(dataType).map(DataType::getComment).map(Comment::getCommentTwo)
				.orElseThrow(() -> new Exception("hi"));
		System.out.println(commentTwoMap);

		// Lis t=>Map Datatype to comment to String using optional
		List<String> commentOneList = commentList.stream().map(Comment::getCommentOne).collect(Collectors.toList());
		commentOneList.stream().forEach(System.out::println);

		//mapping to a single record
		Comment object = null;
		object = commentList.stream().findFirst().map(comm -> {
			Comment c = new Comment();
			c.setCommentOne(comm.getCommentOne());
			return c;
		}).orElse(null);

		System.out.println("logic"+object.getCommentOne());

		
		//printing list
		commentOneList.stream().forEach(System.out::println);
		
		
		List<DataType> dataList=null;
		
		JAVAAIT javaait=new JAVAAIT();
		javaait.createTest();
		
	
		
		
		
		

	}
	
	public  void createTest() {
		Comment commentOne = new Comment();
		Comment commentTwo = new Comment();
		commentOne.setCommentOne("CommentOne");
		commentOne.setCommentTwo("CommentOne");
		commentTwo.setCommentOne("CommentTwo");
		commentTwo.setCommentTwo("CommentTwo");
		List<Comment> commentList = new ArrayList<>();
		commentList.add(commentOne);
		commentList.add(commentTwo);
		List<DataType> datalist=null;
		datalist=commentList.stream().map(this::createCustom).collect(Collectors.toList());
		datalist.stream().forEach(d->System.out.println(d.getAppealApplicationDetails()));
		
	}
	
	public  DataType createCustom(Comment data) {
		DataType c=new DataType();
		c.setAppealApplicationDetails("ANKIT CUSTOM METHOD");
		return c;
		
	}

}
