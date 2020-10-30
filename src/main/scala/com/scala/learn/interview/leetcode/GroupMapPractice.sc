val l = List( List(0,1), List(1,2), List(2,0), List(1,3))
l.groupMap(x => x(0))(x => x(1))

val a = Array("2 n u", "1 a b c").sortBy(_.span(_ != ' ').swap)