<script>
  const TITLE = 'Hey, I am Jhoel';
  const DESCRIPTION = `
I am a web developer that initiated my studies with front-end development, which was sparked by my technical course. Over the years, my career trajectory has transitioned into back-end development, where I have accumulated three years of professional experience (at 2023) working with Java and the Spring ecosystem. 

My expertise extends to diverse application architectures and development methodologies, such as Microservices, Ports and Adapters, Domain-Driven Design, Behavior-Driven Development, and various automated testing practices.

I am well-versed in continuous integration and delivery, as well as system observability. Currently, I work within a global team, which is providing me with the opportunity to enhance my English and remote-working skills.
`;
 
  const USERNAME = '@bagnascojhoel';
  const BLOG_URL = 'https://blog.bagnascojhoel.com.br';
</script>

<section
  class="
    mb-16
  "
>
  <span class="text-primary-variant font-mono text-base">{USERNAME}</span>
  <h1
    class="
      text-primary
      mb-5
      leading-relaxed
      font-mono
      text-3xl
      lg:text-5xl
      font-medium
    "
  >
    {TITLE}
  </h1>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-2 whitespace-pre-line">
    {DESCRIPTION}
  </p>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-5">
    I also have a blog for some things I want to share. 
    <a href={BLOG_URL} target="_blank" rel="noreferrer" class="underline">Checkout my blog</a>.
  </p>
</section>
