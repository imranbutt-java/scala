object A { self =>
  def use(name: String) = self.me(name)
  def me(name: String) = println(s"A... $name")
}
A.use("Hello")

