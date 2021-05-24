# JavaChess
I mean.. it's chess in Java.. and it took too long...
#Movement
It's pretty basic, just use it like regular chess, like A2 will refer to the left most white pawn, and to move it to 1 place forward you will have to A2-A3
##Seriously don't mess it up
It took a long time to make it work and it's still sensitive, it does not test for letters outside of the range
(like W would result in a place not on the board and thus an out of range exception)
#Change Piece
When your pawn reaches the opponents first row a "What would you like to change it to?" message will be displayed and you need to type in the piece you want
IMPORTANT: the name of the piece HAS to start with an upper case letter ex: Rook. Also it cannot be King or Pawn because one is outside the rules and the other would be useless
When King or Pawn or an illegal piece see pavn is typed in the move will be invalid and the player has to type in ex A7:A8 again, and then type in a valid piece.
