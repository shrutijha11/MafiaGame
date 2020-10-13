import java.util.*;

public class Main { //Main class

	public static void main(String[] args){
		// TODO Auto-generated method stub
		Game_list<Player> mafiaplayer_list=new Game_list<Player>(); //creating object of generic class Game_list<T>
		
		initialise_mafiaplayers(mafiaplayer_list); //intialising the players of the game
			
		Game mafia_game=new Game(mafiaplayer_list); //creating object of abstract class Game
		
		mafia_game.start(); //starting mafia game
			


	}
	
	public static void initialise_mafiaplayers(Game_list<Player> mafiaplayer_list)
	{
		System.out.println("Welcome to Mafia");
		System.out.println("Enter number of players (N):");
		
		int n=0;
		
		while(true) {
			try {
				n=takeNinput();
				break;
			}
			catch(LessPlayerException e) //N is less than 6
			{
				System.out.println(e.getMessage());
			}

		}

		initialise(n,mafiaplayer_list); //making list of players based on N
	}
	
	public static int takeNinput() throws LessPlayerException
	{
		int input=Game.takeIntInput();
	
		if(input<6)
			throw new LessPlayerException();
	
		else
			return input;

	}
	
	public static void initialise(int n,Game_list<Player> mafiaplayer_list)
	{
		int total=0;
		int no_healers=Math.max(1,n/10);
		
		for(int i=1; i<=n/5;i++)
		{
			Player new_p=new mafia();
			mafiaplayer_list.getlist().add(new_p);
			total++;
		}
		for(int i=1; i<=n/5;i++)
		{
			Player new_p=new detective();
			mafiaplayer_list.getlist().add(new_p);
			total++;
		}
		for(int i=1; i<=no_healers;i++)
		{
			Player new_p=new healer();
			mafiaplayer_list.getlist().add(new_p);
			total++;
		}
		for(int i=1; i<=n-total;i++)
		{
			Player new_p=new commoner();
			mafiaplayer_list.getlist().add(new_p);
		}
			
		shuffle(mafiaplayer_list); //shuffle the list to randomise players
		
	}
	
	public static void shuffle(Game_list<Player> mafiaplayer_list) //shuffle players list
	{
		Random gen=new Random();
		
		int n = mafiaplayer_list.getlist().size();
		while (n > 1) {
			int k = gen.nextInt(n--);
			Player temp = mafiaplayer_list.getlist().get(n);
			mafiaplayer_list.getlist().set(n,mafiaplayer_list.getlist().get(k));
			mafiaplayer_list.getlist().set(k, temp);
		}
		
		for(int i=0; i<mafiaplayer_list.getlist().size();i++)
		{
			mafiaplayer_list.getlist().get(i).setid(i+1); //set ids of players
		}
		
	}
	
}


abstract class Player implements Comparable<Player> //abstract class Player to defined players of the game
{													//implementing equals and compareTo methods
	private int id; //id of player
	private double HP; //HP value of player
	private boolean alive; //alive status of players
	private boolean voted_out; //to check if player voted_out
	
	public Player(double HP)
	{
		this.HP=HP;
		this.alive=true;
		this.voted_out=false;
		
	}
	
	protected void setHP(double x)
	{
		this.HP=x;
	}
	
	protected void setalive(boolean x)
	{
		this.alive=x;
	}
	
	protected void setvoted_out(boolean x)
	{
		this.voted_out=x;
	}
	
	
	protected int getid()
	{
		return this.id;
	}
	
	protected void setid(int x)
	{
		this.id=x;
	}
	
	protected double getHP()
	{
		return this.HP;
	}
	
	protected boolean getalive()
	{
		return this.alive;
	}
	
	protected boolean getvoted_out()
	{
		return this.voted_out;
	}
	
	
	@Override
    public boolean equals(Object o1){ //equals method of Object class
        if (o1!=null && o1 instanceof Player){ 
        	Player o=(Player) o1;
            return HP==o.HP;
        }
        return false;
    }
	
	@Override
    public int compareTo(Player P) { //compareTo method of Comparable interface
		
        if(this.HP==P.getHP())
        	return 0;
        
        else if(this.HP>P.getHP())
        	return 1;
        
        else
        	return -1;
    }
	
	abstract protected void printDetails(); //method to print details of players


}


class mafia extends Player //concrete subclass mafia of Player
{
	public mafia()
	{
		super(2500);
		
	}
	
	@Override
	public void printDetails()
	{
		System.out.println("mafia "+getid());
	}


}

class detective extends Player //concrete subclass detective of Player
{
	public detective()
	{
		super(800);
		
	}
	
	@Override
	public void printDetails()
	{
		System.out.println("detective "+getid());
	}


}

class healer extends Player //concrete subclass healer of Player
{
	public healer()
	{
		super(800);
		
	}
	
	@Override
	public void printDetails()
	{
		System.out.println("healer "+getid());
	}


}

class commoner extends Player //concrete subclass commoner of Player
{
	public commoner()
	{
		super(1000);
		
	}
	
	@Override
	public void printDetails()
	{
		System.out.println("commoner "+getid());
	}


}

class Game_list<T> //generic class to define list of players for the Game
{
	private ArrayList<T> players_list;
	
	public Game_list()
	{
		players_list=new ArrayList<T>();
	}
	
	public void addlist(T obj)
	{
		players_list.add(obj);
	}
	
	public ArrayList<T> getlist()
	{
		return this.players_list;
	}


}



class Game //class to define Game
{
	final private ArrayList<Player> players; //final list of players of the game
	private int user_id; //id of user playing
	private String winner; 
	
	public Game(Game_list<Player> mafiaplayer_list)
	{
		this.players=mafiaplayer_list.getlist(); 
		this.user_id=0;
		winner="";
	}
	
	public void start() //game starts here
	{
		
		int n=players.size();
		int choice=0; //choice of the player for character
		System.out.println("Choose a Character:\n"+
						"   1) Mafia\n"+
						"   2) Detective\n"+
						"   3) Healer\n"+
						"   4) Commoner\n"+
						"   5) Assign Randomly"
				
				);
		
		while(true)
		{
			choice=takeIntInput();
			
			if(choice>0 && choice<6) //if choice not bw 1 to 5
				break;
			
			else
			{
				System.out.println("Invalid choice. Enter again.");	
			}
			
		}
		
		final Random rand=new Random();
		if(choice==5) //if choice is 5 assign random character bw 1 to 4
		{
			choice=rand.nextInt(4)+1;
		}
		
		switch(choice) //setting id of user player
		{
			case 1:
					for(Player p: players)
					{
						if(p instanceof mafia)
						{
							user_id=p.getid();
							break;
						}
					}
					break;
					
			case 2:
				for(Player p: players)
				{
					if(p instanceof detective)
					{
						user_id=p.getid();
						break;
					}
				}
				break;
				
			case 3:
				for(Player p: players)
				{
					if(p instanceof healer)
					{
						user_id=p.getid();
						break;
					}
				}
				break;
				
			case 4:
				for(Player p: players)
				{
					if(p instanceof commoner)
					{
						user_id=p.getid();
						break;
					}
				}
				break;
					
		
			default:
				break;
		
		}
		
	
		System.out.println("You are Player"+user_id); 
		
		switch(choice) //to print the other mafias/detectives/healers apart from the user player
		{
			case 1: 
				System.out.print("You are a mafia. Other mafias are:[ ");
				for(Player p: players)
				{
					if(p instanceof mafia && p.getid()!=user_id)
					{
						System.out.print("Player"+p.getid()+" ");
					}
				}
				System.out.println("].");
				break;
				
			case 2:
				System.out.print("You are a detective. Other detectives are:[ ");
				for(Player p: players)
				{
					if(p instanceof detective && p.getid()!=user_id)
					{
						System.out.print("Player"+p.getid()+" ");
					}
				}
				System.out.println("].");
				break;
				
			case 3:
				System.out.print("You are a healer. Other healers are:[ ");
				for(Player p: players)
				{
					if(p instanceof healer && p.getid()!=user_id)
					{
						System.out.print("Player"+p.getid()+" ");
					}
				}
				System.out.println("].");
				break;
				
			case 4:
				System.out.println("You are a commoner.");
				break;
				
			default:
				break;
			
		}
		
		gameplay();	//start gameplay i.e. rounds of the game

	}
	
	
	public void gameplay() 
	{
		
		int round_no=1;
		
		while(true) //hold rounds till endgame conditions reached
		{
			round(round_no);
			round_no++;
			
			if(check()==1) //check for endgame conditions
				break;
		
		}
		
		printResults(); //if endgame, print results
			
	}
	
	public int check() //function to check endgame conditions
	{
		int n_mafia=0;
		int n_others=0;
		
		for(Player p: players)
		{
			if(p instanceof mafia && p.getalive()==true && p.getvoted_out()==false)
				n_mafia++;
			
			else if(p.getalive()==true && p.getvoted_out()==false)
				n_others++;
		}
		int ratio=n_mafia/n_others; //the ratio
		
		if(ratio==1) //if ratio is 1, mafia wins
		{
			winner="Mafia";
			return 1;
		}
		else if(n_mafia==0) //if ratio is 0, mafia lose
		{
			winner="Others";
			return 1;
		}
		
		return 0; //game did not end

	}
	

	public void printResults() //print results and role of each player
	{
		System.out.println("Game over");
		if(winner.equalsIgnoreCase("Mafia")) System.out.println("The Mafias have won.");
		
		else 
			System.out.println("The Mafias have lost.");
		
		for(Player p: players )
		{
			if(p instanceof mafia)
				System.out.print("Player"+p.getid()+", ");
		}
		System.out.println("were mafias.");
		
		for(Player p: players )
		{
			if(p instanceof detective)
				System.out.print("Player"+p.getid()+", ");
		}
		System.out.println("were detectives.");
		
		for(Player p: players )
		{
			if(p instanceof healer)
				System.out.print("Player"+p.getid()+", ");
		}
		System.out.println("were healers.");
		
		for(Player p: players )
		{
			if(p instanceof commoner)
				System.out.print("Player"+p.getid()+", ");
		}
		System.out.println("were commoners.");
		
		
	}
	
	
	
	public void round(int r_no) //conduct each round
	{
		System.out.println("Round "+r_no+":"); //print round no.
		Player user=players.get(user_id-1); //user player
		int n_detectives=0;
		int n_mafias=0;
		int n_healers=0;
		int n_commoners=0;
		int n_total=0;
		boolean vote_flag=true; //if voting process needs to be done or not
		
		for(Player p: players)
		{
			if(p instanceof detective && p.getalive()==true && p.getvoted_out()==false)
				n_detectives++;
			
			if(p instanceof mafia && p.getalive()==true && p.getvoted_out()==false)
				n_mafias++;
			if(p instanceof healer && p.getalive()==true && p.getvoted_out()==false)
				n_healers++;
			
			if(p instanceof commoner && p.getalive()==true && p.getvoted_out()==false)
				n_commoners++;
			
			n_total=n_mafias+n_detectives+n_healers+n_commoners;
			
		}
		
		System.out.print(n_total+" players are remaining: "); //print players in game
		for(Player p: players)
		{
			if(p.getalive()==true && p.getvoted_out()==false)
				System.out.print("Player"+p.getid()+" ");
		}
		System.out.println();
		
		int target=0; //id of target of mafia
		if(user instanceof mafia && user.getalive()==true && user.getvoted_out()==false)
		{ //if user is mafia, he decided target
			while(true)
			{
				try {
					target=step1_mafia();
					break;
				}catch(ImpossibleTargetException e) //target is mafia or not alive
				{
					System.out.println(e.getMessage());
				}
				
			}
		}
		
		else if(n_mafias>0) //if user not mafia, then other mafias decide target
		{
			while(true)
			{
				try {
					target=step1_nonmafia();
					System.out.println("Mafias have chosen their target.");
					break;
				}catch(ImpossibleTargetException e)
				{
					continue;
				}
				
			}
		}
		
		if(target>0) HP_calculation(target); //calculate damages
		
		int test=0; //id of player to be tested
		if(user instanceof detective && user.getalive()==true && user.getvoted_out()==false)
		{ //if user is the detective, he decided who to test
			while(true)
			{
				try {
					test=step2_detective();
					break;
				}catch(ImpossibleTestException e) //if player to test is detective or not alive
				{
					System.out.println(e.getMessage());
				}
				
			}
			
			if(test>0 && players.get(test-1) instanceof mafia) //print if tested player mafia or not
			{
		
				System.out.println("Player"+test+ " is a mafia.");
			}
			
			else if(test>0)
				System.out.println("Player"+test+ " is not a mafia.");
			
		}

		else if(n_detectives>0) //if user not detective, other detectives decide target
		{
			while(true)
			{
				try {
					test=step2_nondetective();
					System.out.println("Detectives have chosen player to test.");
					break;
				}catch(ImpossibleTestException e)
				{
					continue;
				}
				
			}
		}

		else
			System.out.println("Detectives have chosen player to test.");
		
		
		int heal=0; //id of player to heal
		if(user instanceof healer && user.getalive()==true && user.getvoted_out()==false)
		{ //if user is healer, he decides who to heal
			while(true)
			{
				try {
					heal=step3_healer();
					break;
				}catch(ImpossibleHealException e) //if player to heal not alive
				{
					System.out.println(e.getMessage());
				}
				
			}
		}
		else if(n_healers>0) //if user not healer, other healers decide who to heal
		{
			while(true)
			{
				try {
					heal=step3_nonhealer();
					System.out.println("Healers have chosen someone to heal.");
					break;
				}catch(ImpossibleHealException e)
				{
					continue;
				}
				
			}
		}

		else
			System.out.println("Healers have chosen someone to heal.");

		
		if(heal>0) players.get(heal-1).setHP(players.get(heal-1).getHP()+500); //heal the selected player
		
		System.out.println("---End of Actions---");
		if(target>0)
		{
			Player target_player=players.get(target-1);
			
			if(target_player.getHP()==0) //if target's HP is 0, he dies
			{
				target_player.setalive(false);
				target_player.setvoted_out(true);
				
				System.out.println("Player"+target_player.getid()+" has died.");
			}
			
			else
			{
			
				System.out.println("No player has died."); //otherwise target stays alive
			}
				
		}
		
		if(check()==1) //check if after killing, end game conditions reached
		{
			System.out.println("------End of Round "+r_no+" ------");
			System.out.println();
			return; //then return
		}
		
		int voted_player=0; //else voting process begins
		if(test>0 && players.get(test-1) instanceof mafia) //if detective tested mafia, them mafia voted out
		{
			voted_player=test;
			vote_flag=false; //no voting process now
			players.get(test-1).setvoted_out(true);
			players.get(test-1).setalive(false);
			
		}
		
		
		if(vote_flag) //if voting process needs to happen(when detective could not test mafia)
		{
			if(user.getalive()==true && user.getvoted_out()==false) //if user in game, ask for vote
			{
				while(true)
				{
					try {
						voted_player=step4_uservoting();
						break;
					}catch(ImpossibleVoteException e)
					{
						System.out.println(e.getMessage());
					}
					
				}
			}
			else //else take others vote
			{
				while(true)
				{
					try {
						voted_player=step4_nonuservoting();
						break;
					}catch(ImpossibleVoteException e)
					{
						continue;
					}
					
				}
			}
		}
		
		if(voted_player>0) //display results of voting process
		{
			Player voted_out_player=players.get(voted_player-1);
			voted_out_player.setalive(false);
			voted_out_player.setvoted_out(true);
			System.out.println("Player"+voted_out_player.getid()+" has been voted out.");
		}
		System.out.println("------End of Round "+r_no+" ------");
		System.out.println(); //round ends
		
		
		
	}
	
	public void HP_calculation(int target) 
	{
		Player tokill=players.get(target-1); //player to kill
		
		ArrayList<Player> mafiaHP =new ArrayList<Player>(); //list of mafia that are in game with HP>0
		double combinedHP=0;
		
		double damage=tokill.getHP(); //damage to mafias
		
		for(Player p:players)
		{
			if(p instanceof mafia && p.getalive()==true && p.getvoted_out()==false && p.getHP()>0)
				{
					mafiaHP.add(p);
					combinedHP+=p.getHP();
				}
		}
		
		if(mafiaHP.size()>0)
		{
			Player combined_mafia=new mafia(); //make a Mafia object with combined HP of all valid mafias
			combined_mafia.setHP(combinedHP);
			
			if(combined_mafia.equals(tokill)) //if Hp of mafias combined equal to target HP
				tokill.setHP(0);
			
			else if(combined_mafia.compareTo(tokill)>0) //if HP of mafias combined greater than target HP
					tokill.setHP(0);
					
			else
				tokill.setHP(tokill.getHP()-combinedHP); //if HP of target greater tha combined HP of mafias
			
			double n_mafia=mafiaHP.size();
			
			double indi_damage=(double)damage/n_mafia; //calculating individual damage to mafias
			
			Collections.sort(mafiaHP,new PlayerComparatorByHP()); //sorting mafias on basis of their HP
			
			for(Player mafia: mafiaHP)
			{
				if(mafia.getHP()<indi_damage) //if mafias HP less than individual damage (x/y)
				{
					damage-=mafia.getHP(); //subtract their HP from total damage
					mafia.setHP(0); //set their HP to 0
					//mafiaHP.remove(mafiaHP.indexOf(mafia));
					n_mafia--;
					
					if(n_mafia>0) indi_damage=damage/n_mafia; //calculate individual damage now
					
					else return;
				}
				else 
					break; //first mafia with HP greater than individual damage(since list is sorted)
			}              //then break from for loop

			
			for(Player mafia:mafiaHP) //subtract indivudla damage now from mafias whose HP greater than individual damage
			{
				if(mafia.getHP()>0) mafia.setHP(mafia.getHP()-indi_damage);
				else mafia.setHP(0);
			}
					
		}
		
	}
	
	public int step1_mafia() throws ImpossibleTargetException //choose target by user player if mafia
	{
		System.out.println("Choose a target.");
		int n=takeIntInput();
		
		if(n>players.size()|| n<1) //if target not bw 1-N
			throw new ImpossibleTargetException();
		
		Player target=players.get(n-1);
		
		if(target instanceof mafia) //if target mafia
			throw new ImpossibleTargetException();
		
		if(target.getalive()!=true && target.getvoted_out()!=false) //if target dead or voted out(not in game)
			throw new ImpossibleTargetException();
		
		else
			return n;
		
		
	}
	
	public int step1_nonmafia() throws ImpossibleTargetException //choose target by other mafia
	{
		Random rand=new Random();
		int n=rand.nextInt(players.size())+1;
		
		Player target=players.get(n-1);
		
		if(target instanceof mafia)
			throw new ImpossibleTargetException();
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleTargetException();
		
		else
			return n;
			
	}
	
	public int step2_detective() throws ImpossibleTestException //choose player to test if user player is detective
	{
		System.out.println("Choose a player to test.");
		int n=takeIntInput();
		
		if(n>players.size() || n<1)
			throw new ImpossibleTestException();
		
		Player target=players.get(n-1);
		
		if(target instanceof detective)
			throw new ImpossibleTestException();
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleTestException();
		
		else
			return n;
		
		
	}
	
	public int step2_nondetective() throws ImpossibleTestException //choose player to test by other detectives
	{
		Random rand=new Random();
		int n=rand.nextInt(players.size())+1;
		
		Player target=players.get(n-1);
		
		if(target instanceof detective)
			throw new ImpossibleTestException();
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleTestException();
		
		else
			return n;
			
	}
	
	public int step4_uservoting() throws ImpossibleVoteException //choose player to vote by user player
	{
		System.out.println("Choose a player to vote out:");
		int n=takeIntInput();
		
		if(n>players.size()|| n<1)
			throw new ImpossibleVoteException();
		
		Player target=players.get(n-1);
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleVoteException();
		
		int f_user=0;
		while(true)
		{
			try {
				f_user=step4_nonuservoting();
				break;
			}catch(ImpossibleVoteException e)
			{
				continue;
			}
			
		}
		
		f_user=Math.max(f_user, n);
		
		return f_user;
			
	}
	
	public int step4_nonuservoting() throws ImpossibleVoteException //choose player to vote by non user players
	{
		Random rand=new Random();
		int n=rand.nextInt(players.size())+1;
		
		Player target=players.get(n-1);
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleVoteException();
		
		else
			return n;
			
	}
	
	public int step3_healer() throws ImpossibleHealException //choose player to heal if user player is healer
	{
		System.out.println("Choose a person to heal:");
		int n=takeIntInput();
		
		if(n>players.size())
			throw new ImpossibleHealException();
		
		Player target=players.get(n-1);
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleHealException();
		
		else
			return n;
		
	}
	
	public int step3_nonhealer() throws ImpossibleHealException //choose player to heal by other healers
	{
		Random rand=new Random();
		int n=rand.nextInt(players.size())+1;
		
		Player target=players.get(n-1);
		
		if(target.getalive()!=true && target.getvoted_out()!=false)
			throw new ImpossibleHealException();
		
		else
			return n;
			
	}
	
	
	public static int takeIntInput() { //function to take int inputs
		
		Scanner in=new Scanner(System.in);
		
		while (true){
			String input = in.next();
			try {
					return Integer.parseInt(input);
            	} catch (Exception ne) {
            		System.out.println("Input is not an Integer. Enter again:");
            	}
		}
	}
	
	
}



class PlayerComparatorByHP implements Comparator<Player> { //Comparator implementing class to compare HP of players

    @Override
    public int compare(Player o1, Player o2) {
    	if(o1.getHP()==o2.getHP())
        	return 0;
        
        else if(o1.getHP()>o2.getHP())
        	return 1;
        
        else
        	return -1;
    }

}


class LessPlayerException extends Exception //if N is less than 6
{
	public LessPlayerException()
	{
		super("Min. players should be 6. Enter N again.");
	}

}


class ImpossibleTargetException extends Exception //if target chosen is mafia or not in game
{
	public ImpossibleTargetException()
	{
		super("Cannot target a player who is Mafia or not in the game. Enter target again.");
	}

}

class ImpossibleTestException extends Exception //if test player chosen detective or not in game
{
	public ImpossibleTestException()
	{
		super("Cannot test a player who is detective or not in the game. Enter player to test again.");
	}

}

class ImpossibleHealException extends Exception //if player to heal not in game
{
	public ImpossibleHealException()
	{
		super("Cannot heal a player not in the game. Enter player to heal again.");
	}

}

class ImpossibleVoteException extends Exception //if voted person not in game
{
	public ImpossibleVoteException()
	{
		super("Cannot vote out a player not in the game. Enter player to vote out again.");
	}

}


