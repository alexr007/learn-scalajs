package learn_sjs

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("app")
object SJS003Polygon {
  import scala.math.{max, min, atan2, toDegrees}

  case class Pt(x: Int, y: Int) {
    def this(x: Double, y: Double) = this(x.toInt, y.toInt)
    def this(xy: Array[Int]) = this(xy(0), xy(1))
  }

  private def centroid(pts: List[Pt]): Pt = {
    val len = pts.length
    val (sumx, sumy) = pts.foldLeft((0.toDouble,0.toDouble))((a, p) => (a._1+p.x, a._2+p.y))
    new Pt(sumx/len, sumy/len)
  }

  private def sortPoints(pts: List[Pt]): List[Pt] = {
    def angle(dx: Double, dy: Double) = (toDegrees(atan2(dx, dy)) + 360) % 360
    def anglePt(a: Pt, c: Pt) = angle(a.x - c.x, a.y - c.y)
    val c = centroid(pts)
    pts.sortWith { (a, b) => anglePt(a, c) < anglePt(b, c) }
  }

  private def normalize(pts: List[Pt])(width: Int, height: Int): List[Pt] = {
    val (minx, miny, maxx, maxy) = pts.foldLeft((
      Double.MaxValue,
      Double.MaxValue,
      Double.MinPositiveValue,
      Double.MinPositiveValue
    )) { (acc, pt) => (
      min(acc._1, pt.x),
      min(acc._2, pt.y),
      max(acc._3, pt.x),
      max(acc._4, pt.y)
    ) }
    val (dx, dy) = (maxx - minx, maxy - miny)
    val scale = min(width/dx, height/dy)
    val normalize = (offset: Double) => (x: Int) => (x-offset)*scale
    val normx = normalize(minx)
    val normy = normalize(miny)
    val scaled: List[Pt] = pts map { p => new Pt(normx(p.x), normy(p.y))}
    scaled
  }

  val canvasW = 1900
  val canvasH = 940

  val pointsUnsorted: List[Pt] = Data.points
//  val points:         List[Pt] = sortPoints(pointsUnsorted)
  val points = pointsUnsorted
  val pointsNormFn: (Int, Int) => List[Pt] = normalize(points)

  @JSExport
  def runner(): Unit = {
    val canvas: Canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    canvas.width = canvasW
    canvas.height = canvasH
    dom.document.body.appendChild(canvas)
    main(canvas)
  }

  def main(canvas: html.Canvas): Unit = {
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[dom.CanvasRenderingContext2D]

    def clear() = {
      ctx.fillStyle = "white"
      ctx.fillRect(0, 0, canvasW, canvasH)
    }

    def run = {
      val h::t = pointsNormFn(canvasW, canvasH)
      ctx.beginPath()
      ctx.moveTo(h.x, h.y)
      t foreach { p => ctx.lineTo(p.x, p.y)}
      ctx.lineTo(h.x, h.y)
      ctx.stroke()
    }
    run
//    dom.window.setInterval(() => run, 100)
  }
}
