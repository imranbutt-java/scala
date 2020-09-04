case class Point(x: Int, y: Int)

val xs = List(1)
val ys = List(-2, 7)

// Question 1
val q1 = xs.flatMap(x => {
  ys.map(y => {
    Point(x, y)
  })
})

for {
  x <- xs
  y <- ys
} yield Point(x, y)

// Question 2
case class Point3d(x: Int, y: Int, z: Int)

val zs = List(3, 4)
for {
  x <- xs
  y <- ys
  z <- zs
} yield Point3d(x, y, z)

// Question 3
xs.flatMap(x => {
  ys.flatMap(y => {
    zs.map(z => Point3d(x, y, z))
  })
})