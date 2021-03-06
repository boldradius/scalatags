package scalatags

import org.scalatest._
import scala.xml._

import Util._

/**
 * A set of examples used in the documentation.
 */
class ExampleTests extends FreeSpec{
  "Hello World" in xmlCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p("This is my first paragraph"),
          p("This is my second paragraph")
        )
      )
    ),
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <p>This is my first paragraph</p>
          <p>This is my second paragraph</p>
        </div>
      </body>
    </html>
  )
  "Variables" in xmlCheck(
    {
      val title = "title"
      val numVisitors = 1023

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my ", title),
          div(
            p("This is my first paragraph"),
            p("you are the ", numVisitors.toString, "th visitor!")
          )
        )
      )
    },
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <p>This is my first paragraph</p>
          <p>you are the 1023th visitor!</p>
        </div>
      </body>
    </html>
  )
  "Control Flow" in xmlCheck(
    {
      val numVisitors = 1023
      val posts = Seq(
        ("alice", "i like pie"),
        ("bob", "pie is evil i hate you"),
        ("charlie", "i like pie and pie is evil, i hat myself")
      )

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          div("posts"),
          for ((name, text) <- posts) yield (
            div(
              h2("Post by ", name),
              p(text)
            )
            ),
          if(numVisitors > 100)(
            p("No more posts!")
            )else(
            p("Please post below...")
            )
        )
      )
    },
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>posts</div>
        <div>
          <h2>Post by alice</h2>
          <p>i like pie</p>
        </div>
        <div>
          <h2>Post by bob</h2>
          <p>pie is evil i hate you</p>
        </div>
        <div>
          <h2>Post by charlie</h2>
          <p>i like pie and pie is evil, i hat myself</p>
        </div>
        <p>No more posts!</p>
      </body>
    </html>
  )
  "Functions" in xmlCheck(
    {
      def imgBox(src: String, text: String) =
        div(
          img.src(src),
          div(
            p(text)
          )
        )

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          imgBox("www.mysite.com/imageOne.png", "This is the first image displayed on the site"),
          div.cls("content")(
            p("blah blah blah i am text"),
            imgBox("www.mysite.com/imageTwo.png", "This image is very interesting")
          )
        )
      )
    },
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <img src="www.mysite.com/imageOne.png"></img>
          <div>
            <p>This is the first image displayed on the site</p>
          </div>
        </div>
        <div class="content ">
          <p>blah blah blah i am text</p>
          <div>
            <img src="www.mysite.com/imageTwo.png"></img>
            <div>
              <p>This image is very interesting</p>
            </div>
          </div>
        </div>
      </body>
    </html>
  )
  "Custom Attributes" in xmlCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p.attr("onclick" -> "... do some js")(
            "This is my first paragraph"
          ),
          a.attr("href" -> "www.google.com")(
            p("Goooogle")
          )
        )
      )
    ),
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <p onclick="... do some js">This is my first paragraph</p>
          <a href="www.google.com">
            <p>Goooogle</p>
          </a>
        </div>
      </body>
    </html>
  )
  "Attributes" in xmlCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        div(
          p.onclick("... do some js")(
            "This is my first paragraph"
          ),
          a.href("www.google.com")(
            p("Goooogle")
          )
        )
      )
    ),
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <p onclick="... do some js">This is my first paragraph</p>
          <a href="www.google.com">
            <p>Goooogle</p>
          </a>
        </div>
      </body>
    </html>
  )
  "Classes an CSS" in xmlCheck(
    html(
      head(
        script("some script")
      ),
      body(
        h1.css("color" -> "red", "background-color" -> "blue")("This is my title"),
        div.color("red").background_color("blue")(
          p.cls("contentpara", "first")(
            "This is my first paragraph"
          ),
          a.opacity(0.9)(
            p.cls("contentpara")("Goooogle")
          )
        )
      )
    ),
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1 style="color: red; background-color: blue; ">This is my title</h1>
        <div style="color: red; background-color: blue; ">
          <p class="contentpara first ">This is my first paragraph</p>
          <a style="opacity: 0.9; ">
            <p class="contentpara ">Goooogle</p>
          </a>
        </div>
      </body>
    </html>
  )


  "Layouts" in xmlCheck(
  {
    def page(scripts: Seq[STag], content: Seq[STag]) =
      html(
        head(scripts),
        body(
          h1("This is my title"),
          div.cls("content")(content)
        )
      )


    page(
      Seq(
        script("some script")
      ),
      Seq(
        p("This is the first ", b("image"), " displayed on the ", a("site")),
        img.src("www.myImage.com/image.jpg"),
        p("blah blah blah i am text")
      )
    )

  },
  <html>
    <head>
      <script>some script</script>
    </head>
    <body>
      <h1>This is my title</h1>
      <div class="content ">
        <p>
          This is the first
          <b>image</b>
          displayed on the
          <a>site</a>
        </p>
        <img src="www.myImage.com/image.jpg"></img>
        <p>blah blah blah i am text</p>
      </div>
    </body>
  </html>
  )



  "Inheritence" in xmlCheck(
    {
      class Parent{
        def render = html(
          headFrag,
          bodyFrag

        )
        def headFrag = head(
          script("some script")
        )
        def bodyFrag = body(
          h1("This is my title"),
          div(
            p("This is my first paragraph"),
            p("This is my second paragraph")
          )
        )
      }

      object Child extends Parent{
        override def headFrag = head(
          script("some other script")
        )
      }

      Child.render
    },
    <html>
      <head>
        <script>some other script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <div>
          <p>This is my first paragraph</p>
          <p>This is my second paragraph</p>
        </div>
      </body>
    </html>
  )



  "Unparsed" in xmlCheck(
    {
      val input = "<p>i am a cow</p>"

      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          Unparsed(input)
        )
      )
    },
    <html>
      <head>
        <script>some script</script>
      </head>
      <body>
        <h1>This is my title</h1>
        <p>i am a cow</p>
      </body>
      </html>
  )

  "Inline XML" in {
    xmlCheck(
      html(
        head(
          <script>Stuff Inside</script>,
          link()
        ),
        body(
          <div>
            <h1>Title</h1>
            <p>Stuff</p>
          </div>
        )
      ),
      <html>
        <head>
          <script>Stuff Inside</script>
          <link></link>
        </head>
        <body>
          <div>
            <h1>Title</h1>
            <p>Stuff</p>
          </div>
        </body>
      </html>
    )
  }
}