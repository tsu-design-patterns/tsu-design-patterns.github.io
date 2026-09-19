# TSU Design Patterns course website

The Jekyll website for the Design Patterns course at **Tbilisi State University**: a 14-week curriculum covering all 23 GoF patterns, one lesson per week.

## Run locally

The only requirement is Ruby with Bundler. From this directory:

```shell
bundle install
bundle exec jekyll serve
```

Open [localhost:4000](http://localhost:4000). Restart the server after changing `_config.yml`.

## Styling, icons and diagrams

There is no build step besides Jekyll. These libraries are loaded from a CDN in `_layouts/default.html`:

- **[Tailwind CSS](https://tailwindcss.com/docs/installation/play-cdn)**: style pages with utility classes directly in the HTML. The only CSS in the project is the small `<style type="text/tailwindcss">` block in that layout. It uses `@apply` to style lesson Markdown, which has no classes, and to color code highlighted by Jekyll.
- **[Font Awesome Free](https://fontawesome.com/search?ic=free)**: for example, `<i class="fa-solid fa-arrow-right" aria-hidden="true"></i>`.
- **[Mermaid](https://mermaid.js.org/)**: write a diagram in a ```` ```mermaid ```` code block, like the class diagram in `week-01.md`. Mermaid is only downloaded on pages that contain a diagram.

## Add a lesson

1. Copy `week-01.md` to `week-02.md` and update its front matter: `permalink`, `week`, `title`, `description`, `category_label`, `pattern_count`, `patterns`, and `sections`. Each `sections` entry links to a `##` heading marked with a matching `{: #id }`.
2. Put runnable examples in `examples/week-02/` and show them in the lesson with `include_relative` inside a `highlight java` block, as Week 1 does.
3. When the lesson is ready, add its `url` to week 2 in `_data/weeks.yml`. The home page then marks it as available, and the previous lesson links to it.

To check an example before publishing, compile and run it with JDK 21 or newer, for example `javac *.java && java StrategyDemoTest` in `examples/week-01`.

## Publish

Every push to `main` runs `.github/workflows/pages.yml`, which builds the site with Jekyll and deploys it to [tsu-design-patterns.github.io](https://tsu-design-patterns.github.io). Before the first deployment, set **Settings → Pages → Build and deployment → Source** to **GitHub Actions**.
