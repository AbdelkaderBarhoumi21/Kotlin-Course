package DSL

// ─────────────────────────────────────────────
// @DslMarker prevents accidentally calling an
// outer-scope method from inside a nested block.
// Both HTML and Body carry this annotation, so
// you can't write html { body { body { } } } by
// reaching back into the HTML receiver.
// ─────────────────────────────────────────────
@DslMarker
annotation class HtmlDsl

// ── Attribute helper ─────────────────────────
// Every tag that supports an `id` will inherit
// this class so the property is defined once.
abstract class Tag(val tagName: String) {
    var id: String = ""
    var cssClass: String = ""

    // Builds the opening tag with optional attrs
    protected fun openTag(): String {
        val attrs = buildString {
            if (id.isNotEmpty()) append(""" id="$id"""")
            if (cssClass.isNotEmpty()) append(""" class="$cssClass"""")
        }
        return "<$tagName$attrs>"
    }

    protected fun closeTag() = "</$tagName>"
}

// ── Leaf elements (no children) ───────────────
@HtmlDsl
class H1 : Tag("h1") {
    var text: String = ""
    fun build() = "${openTag()}$text${closeTag()}"
}

@HtmlDsl
class P : Tag("p") {
    var text: String = ""
    fun build() = "${openTag()}$text${closeTag()}"
}

@HtmlDsl
class A : Tag("a") {
    var href: String = ""
    var text: String = ""

    // A has an extra attribute, so we override openTag logic
    fun build(): String {
        val attrs = buildString {
            if (id.isNotEmpty()) append(""" id="$id"""")
            if (cssClass.isNotEmpty()) append(""" class="$cssClass"""")
            if (href.isNotEmpty()) append(""" href="$href"""")
        }
        return "<a$attrs>$text</a>"
    }
}

// ── Container elements ────────────────────────
@HtmlDsl
class Body : Tag("body") {
    private val children = mutableListOf<String>()

    fun h1(init: H1.() -> Unit) {
        val el = H1().apply(init)
        children.add(el.build())
    }

    fun p(init: P.() -> Unit) {
        val el = P().apply(init)
        children.add(el.build())
    }

    fun a(init: A.() -> Unit) {
        val el = A().apply(init)
        children.add(el.build())
    }

    fun build() = "${openTag()}\n  ${children.joinToString("\n  ")}\n${closeTag()}"
}

@HtmlDsl
class Head : Tag("head") {
    private var titleText = ""
    private var metaDescription = ""

    fun title(text: String) { titleText = text }
    fun meta(description: String) { metaDescription = description }

    fun build() = buildString {
        append("<head>")
        if (titleText.isNotEmpty()) append("\n  <title>$titleText</title>")
        if (metaDescription.isNotEmpty()) append("""\n  <meta name="description" content="$metaDescription">""")
        append("\n</head>")
    }
}

@HtmlDsl
class HTML : Tag("html") {
    private var head: Head? = null
    private var body: Body? = null

    fun head(init: Head.() -> Unit) { head = Head().apply(init) }
    fun body(init: Body.() -> Unit) { body = Body().apply(init) }

    fun build() = buildString {
        append("<html>\n")
        head?.let { append("${it.build()}\n") }
        body?.let { append("${it.build()}\n") }
        append("</html>")
    }
}

// ── Entry-point builder function ──────────────
// The lambda `init` has HTML as its receiver, so
// inside the block you call head/body directly.
fun html(init: HTML.() -> Unit): String = HTML().apply(init).build()

// ─────────────────────────────────────────────
fun main() {

    // ── Example 1: basic page ─────────────────
    val basicPage = html {
        head {
            title("My Page")
            meta("A simple Kotlin DSL demo")
        }
        body {
            h1 { text = "Welcome" }
            p  { text = "This is a paragraph." }
        }
    }
    println("── Example 1: basic page ──")
    println(basicPage)

    // ── Example 2: elements with id & class ───
    // `id` and `cssClass` come from the Tag base
    // class and are available in every element.
    println("\n── Example 2: id + class attributes ──")
    val styledPage = html {
        head { title("Styled Page") }
        body {
            id = "main-body"             // <body id="main-body">

            h1 {
                id = "hero-title"        // <h1 id="hero-title">
                text = "Hello, DSL!"
            }

            p {
                id = "intro"
                cssClass = "lead"        // <p id="intro" class="lead">
                text = "Kotlin DSLs are expressive and type-safe."
            }

            a {
                id = "cta-link"
                cssClass = "btn btn-primary"
                href = "https://kotlinlang.org"
                text = "Learn Kotlin"
            }
        }
    }
    println(styledPage)

    // ── Example 3: multiple paragraphs ────────
    println("\n── Example 3: blog post ──")
    val post = html {
        head { title("Blog Post") }
        body {
            h1 { id = "post-title"; text = "Understanding Kotlin DSLs" }
            p  { id = "para-1"; text = "DSLs let you express domain concepts naturally." }
            p  { id = "para-2"; text = "They use lambdas with receivers under the hood." }
            a  { id = "read-more"; href = "/more"; text = "Read more..." }
        }
    }
    println(post)
}
