package example
package roll
import collection.mutable
import scala.scalajs.js
import org.scalajs.dom.extensions._
import org.scalajs.dom
import example.cp
import example.cp.Implicits._
import example.cp.SegmentQueryInfo

class Lasers(player: Form,
             laserElement: dom.HTMLElement,
             query: (cp.Vect, cp.Vect) => SegmentQueryInfo,
             kill: => Unit){
  var strokeWidth = 1
  case class Laser(start: cp.Vect,
                   end: cp.Vect,
                   var hit: Option[cp.Vect])
  val lasers: Seq[Laser] =
    laserElement
      .children
      .map{ case (e: dom.SVGLineElement) =>
        new Laser((e.x1, e.y1), (e.x2, e.y2), None)
      }

  def update() = {
    for (laser <- lasers){
      laser.hit = None
      val res = query(laser.start, laser.end)
      if(res != null){
        laser.hit = Some(laser.start + (laser.end - laser.start) * res.t)
        if (res.shape.getBody == player.body) kill
      }
    }
  }

  def draw(ctx: dom.CanvasRenderingContext2D) = {
    for(laser <- lasers){
      ctx.strokeStyle = Color.Red.toString()
      ctx.fillStyle = Color.Red.toString()
      strokeWidth += 1
      ctx.lineWidth = (math.sin(strokeWidth / 5) + 1) * 1 + 2
      val realEnd = laser.hit match{
        case None => laser.end
        case Some(hit) =>
          ctx.fillCircle(hit.x, hit.y, ctx.lineWidth)
          hit
      }

      ctx.strokePathOpen(laser.start, realEnd)
    }
  }
}
