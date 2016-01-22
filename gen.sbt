import java.util.Locale.ENGLISH

TaskKey[Unit]("generateTypeClasses") := {
  TypeClass.all.map{ t =>
    val selfTypes =
      if(t.parents.isEmpty) ""
      else {
        t.parents.map(_.name + "[F]").mkString(" self: ", " with ", " =>")
      }

    val getParentTypeClasses = (t :: t.parents).map{ t =>
      s"  override def ${t.name.toLowerCase(ENGLISH)}: ${t.name}[F] = this"
    }.mkString("\n")

    val Extends = "Extends"

    val extendsList = t.parents match {
      case Nil => "" 
      case parents  =>
        parents.map(p => s"$Extends${p.name}[F]").mkString(" extends ", " with ", "")
    }

    val src = s"""package scalaz

trait $Extends${t.name}[F[_]]$extendsList{
  def ${t.name.toLowerCase(ENGLISH)}: ${t.name}[F]
}

////
/**
  *
  */
////
trait ${t.name}[F[_]] extends $Extends${t.name}[F] {$selfTypes
$getParentTypeClasses
  ////
  ////
}

object ${t.name} {
  def apply[F[_]](implicit F: ${t.name}[F]): ${t.name}[F] = F

  ////
  ////
}
"""
    val dir = (scalaSource in Compile).value / "scalaz"
    val sourceFile = dir / (t.name + ".scala")
    if(sourceFile.isFile) {
      val old = IO.read(sourceFile)
      val newSource = updateSource(src, old)
      IO.write(sourceFile, newSource)
    } else {
      IO.write(sourceFile, src)
    }
  }
}

def updateSource(baseSource: String, oldSource: String): String = {
  val delimiter = "////"
  def parse(text: String): Seq[String] = {
    text.split(delimiter)
  }
  val oldChunks: Seq[String] = parse(oldSource)
  val newChunks: Seq[String] = parse(baseSource)
  if (oldChunks.length != newChunks.length) sys.error("different number of chunks in old and new source")

  val updatedChunks = for {
    ((o, n), i) <- oldChunks.zip(newChunks).zipWithIndex
  } yield {
    val useOld = i % 2 == 1
    if (useOld) o else n
  }
  updatedChunks.mkString(delimiter)
}
